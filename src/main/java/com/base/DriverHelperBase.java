package com.base;

import com.ApplicationManager;
import com.base.HelperBase;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class DriverHelperBase extends HelperBase {

    public WebDriver driver;
    public boolean acceptNextAlert = true;
    private WebDriverWait wait;

    public DriverHelperBase(ApplicationManager manager) {
        super(manager);
        this.driver = manager.getDriver();
        wait = new WebDriverWait(driver,10);
    }

    public String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    protected void type(By locator, String text) {
        if (text != null) {
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(text);
        }

    }

    public WebElement findElement(By className) {
        return driver.findElement(className);
    }

    protected void openUrl(String url) {
        driver.get(manager.getProperty("baseUrl") + url);
    }
    protected void openAbsoluteUrl(String url) {
        driver.get(url);
    }

    public void click(By locator) {
        driver.findElement(locator).click();
    }

    protected void selectGroupByIndex(int index) {
        click(By.xpath("//input[@name='selected[]'][" + (index + 1) + "]"));
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

}
