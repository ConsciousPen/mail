package com.pages;

import org.openqa.selenium.By;

public class SignUpPage {

    public By inputUsername = By.xpath("//*[@name='username']");
    public By inputEmail = By.xpath("//*[@name='email']");

    public By buttonSignUp = By.xpath("//*[@value='Signup' or @value='submit']");

    public By linkLoginPage = By.xpath("//a[contains(text(),'Login')]");
    public By linkLostYourPassword = By.xpath("//a[contains(text(),'Lost your password?')]");

    public By lableInformationMessageTable = By.xpath("//div[@align='center']//table");

}
