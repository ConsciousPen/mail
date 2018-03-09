package com.helpers;

import com.ApplicationManager;
import com.User;
import com.base.DriverHelperBase;
import com.pages.LoginPage;
import com.pages.SignUpPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountHelper extends DriverHelperBase {

    private LoginPage loginPage = new LoginPage();
    private SignUpPage signUpPage = new SignUpPage();

    public AccountHelper(ApplicationManager applicationManager) {
        super(applicationManager);
    }

    public void signUp(User user) {
        openUrl("");
        clickSignupForNewAccount();
        fillUsername(user.login);
        fillEmail(user.email);
        clickConfirmSignUp();

        /*long now = System.currentTimeMillis();
        long deadline = now + 5000;
        while (now < deadline) {
            try {
                wait(deadline - now);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            now = System.currentTimeMillis();
        }*/

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String message = manager.getEmailHelper().getNewMail(user.login, user.password);
        String confirmationLink = extractConfirmationLink(message);

        openAbsoluteUrl(confirmationLink);
        type(By.name("password"), user.password);
        type(By.name("password_confirm"), user.password);
        click(By.xpath("//*[@value='Update User' or @value='submit']"));
    }

    private void clickConfirmSignUp() {
        click(signUpPage.buttonSignUp);
    }

    private void clickSignupForNewAccount() {
        click(loginPage.linkSignupForNewAccount);
    }

    private void fillEmail(String mail) {
        type(signUpPage.inputEmail, mail);
    }

    private void fillUsername(String username) {
        type(signUpPage.inputUsername, username);
    }

    public boolean verifyUserLogged(User user) {
        WebElement webElement = driver.findElement(By.className("login-info-left"));
        return webElement.getText().contains(user.login);
    }

    public String extractConfirmationLink(String text) {
        Pattern regex = Pattern.compile("http\\S*");
        Matcher matcher = regex.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "";
        }
    }

    public void login(User user) {
        openUrl("");
        type(loginPage.inputUsername, user.login);
        type(loginPage.inputPassword, user.password);
        click(loginPage.buttonSubmit);
    }
}
