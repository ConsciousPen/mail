package com.helpers;

import com.ApplicationManager;
import com.User;
import com.base.HelperBase;
import com.gargoylesoftware.htmlunit.ThreadedRefreshHandler;
import com.gargoylesoftware.htmlunit.WaitingRefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;


import java.util.List;

public class AdminHelper extends HelperBase {
    private WebClient webClient;
    private String baseUrl;

    Logger log = LoggerFactory.logger(AdminHelper.class);

    public AdminHelper(ApplicationManager manager) {
        super(manager);
        webClient = new WebClient();
        webClient.setRefreshHandler(new ThreadedRefreshHandler());
        baseUrl = manager.getProperty("baseUrl");
    }

    public boolean userExist(User user) throws Exception {
        HtmlPage userPage = openUserPage(user);
        HtmlForm deleteForm = userRemovalForm(userPage);

        return deleteForm != null;
    }

    private HtmlPage openUserPage(User user) throws Exception {
        login();

        String userId = manager.getHibernateHelper().getUserId(user);
        log.info("userId = " + userId);
        return (HtmlPage) webClient.getPage(baseUrl + "/manage_user_edit_page.php?user_id=" + userId);
    }

    private void login() throws Exception {
        HtmlPage mainPage = (HtmlPage) webClient.getPage(baseUrl);
        log.debug("baseUrl page opened");
        HtmlForm loginForm = mainPage.getFormByName("login_form");
        log.info("loginForm = " + loginForm);
        if (loginForm != null) {
            loginForm.getInputByName("username").setValueAttribute(manager.getProperty("admin.login"));
            loginForm.getInputByName("password").setValueAttribute(manager.getProperty("admin.password"));
            loginForm.getInputByValue("Login").click();
            log.info("login performed");
        }
    }

    private HtmlForm userRemovalForm(HtmlPage page) {
        List<HtmlForm> forms = page.getForms();
        for (HtmlForm form : forms) {
            if (form.getActionAttribute().endsWith("manage_user_delete.php")) {
                return form;
            }
        }
        return null;
    }

    public void deleteUser(User user) throws Exception {
        log.info("Deleting user " + user.login);
        HtmlPage userPage = openUserPage(user);
        log.info("user Page opened");
        HtmlForm deleteForm = userRemovalForm(userPage);
        log.info("delete  " + deleteForm);
        if (deleteForm != null) {
            HtmlPage deleteConfirmation = (HtmlPage) deleteForm.getInputByValue("Delete User").click();
            log.info("Delete User click");
            HtmlForm confirmAdmPassword = userRemovalForm(deleteConfirmation);
            log.info("confirm admin Password");
            confirmAdmPassword.getInputByValue("Delete Account").click();
            log.info("Delete Account click");
        }
    }
}
