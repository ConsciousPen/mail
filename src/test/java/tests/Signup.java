package tests;

import com.User;
import com.base.TestBase;
import com.helpers.AccountHelper;
import com.helpers.JamesHelper;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Signup extends TestBase {
    User user = new User().setLogin("user1").setPassword("passwordUser1").setEmail("testuser1@localhost");
    JamesHelper james;
    AccountHelper account;

    @BeforeClass
    public void preconds() {
        james = app.getJamesHelper();
        account = app.getAccountHelper();

        if (!james.doesUserExist(user.login)) {
            james.createUser(user.login, user.password);
        }
    }

    @Test()
    public void testSignUp() {
        account.signUp(user);
        account.login(user);
        Assert.assertTrue(account.verifyUserLogged(user));
    }


    @AfterClass
    public void deleteEmail() {
        if (james.doesUserExist(user.login)) {
            james.deleteUser(user.login);
        }
    }
}
