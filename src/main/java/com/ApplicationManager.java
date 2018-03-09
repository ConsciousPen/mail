package com;

import com.helpers.AccountHelper;
import com.helpers.EmailHelper;
import com.helpers.HibernateHelper;
import com.helpers.JamesHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class ApplicationManager {
    public String baseUrl;
    private WebDriver driver;

    private HibernateHelper hibernateHelper;
    private AccountHelper accountHelper;
    private EmailHelper emailHelper;
    private JamesHelper jamesHelper;

    private Properties properties;

    public ApplicationManager(Properties properties) {
        this.properties = properties;
      //  appModel.setContacts(getHibernateHelper().getListOfContacts());
    }

    public void stop() {
        driver.quit();
    }

    public WebDriver getDriver() {
        String browser = properties.getProperty("browser");
        if (driver == null) {
            if ("firefox".equals(browser)) {
                driver = new FirefoxDriver();
                //System.setProperty("webdriver.firefox.marionette", "C:\\repo\\exercises\\test_excercises_new\\src\\test\\resources\\geckodriver.exe");
                //System.setProperty("webdriver.gecko.driver", "C:\\repo\\exercises\\test_excercises_new\\src\\test\\resources\\geckodriver.exe");
            } else if ("googlechrome".equals(browser)) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");

                File chromeDriver = new File("src\\test\\resources\\chromedriver.exe");
                System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
                driver = new ChromeDriver(options);
            } else {
                throw new Error("Unsupported Browser");
            }
            baseUrl = properties.getProperty("baseUrl");
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            //navigationHelper = new NavigationHelper(this);
            //groupHelper = new GroupHelper(this);
            //contactHelper = new ContactHelper(this);
            driver.get(baseUrl);
        }
        return driver;
    }

    public String getProperty(String key) {
       return  properties.getProperty(key);
    }

    public AccountHelper getAccountHelper() {
        if (accountHelper == null) {
            accountHelper = new AccountHelper(this);
        }
        return accountHelper;
    }

    public EmailHelper getEmailHelper() {
        if (emailHelper == null) {
            emailHelper = new EmailHelper(this);
        }
        return emailHelper;
    }

    public JamesHelper getJamesHelper() {
        if (jamesHelper == null) {
            jamesHelper = new JamesHelper(this);
        }
        return jamesHelper;
    }

    public HibernateHelper getHibernateHelper() {
        if (hibernateHelper == null) {
            hibernateHelper = new HibernateHelper(this);
        }
        return hibernateHelper;
    }

}
