package tests;

import com.User;
import com.base.TestBase;
import com.helpers.AccountHelper;
import com.helpers.AdminHelper;
import com.helpers.JamesHelper;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.testng.Assert.fail;

public class Signup extends TestBase {
    private User user = new User().setLogin("user1").setPassword("passwordUser1").setEmail("testuser1@localhost");
    JamesHelper james;
    AccountHelper account;
    AdminHelper admin;

    @BeforeClass
    public void preconds() {
        james = app.getJamesHelper();
        account = app.getAccountHelper();
        admin = app.getAdminHelper();

        if (!james.doesUserExist(user.login)) {
            james.createUser(user.login, user.password);
        }
    }

    @Test()
    public void testSignUp() throws Exception {
        admin.deleteUser(user);
        account.signUp(user);
        account.login(user);
        Assert.assertTrue(account.verifyUserLogged(user));
    }

    @Test
    public void testNegativeSignUp() {
        //if (! admin.userExist(user)) {
        //	admin.createUser(user);
        //}
        try {
            account.signUp(user);
        } catch (Exception e) {
            assertThat(e.getMessage(), containsString("That username is already being used"));
            return;
        }
        fail("Exception expected");
    }



    @AfterClass
    public void deleteEmail() {
        if (james.doesUserExist(user.login)) {
            james.deleteUser(user.login);
        }
    }
}
