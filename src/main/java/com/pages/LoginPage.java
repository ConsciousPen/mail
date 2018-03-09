package com.pages;

import org.openqa.selenium.By;

public class LoginPage {

    public By inputUsername = By.xpath("//*[@name='username']");
    public By inputPassword = By.xpath("//*[@name='password']");

    public By checkBoxRememberMe = By.xpath("//*[@name='perm_login']");
    public By checkBoxSecureSession = By.xpath("//*[@name='secure_session']");

    public By buttonSubmit = By.xpath("//*[@type='submit']");

    public By linkSignupForNewAccount = By.xpath("//a[contains(text(),'Signup for a new account')]");
    public By linkLostYourPassword = By.xpath("//a[contains(text(),'Lost your password?')]");

}
