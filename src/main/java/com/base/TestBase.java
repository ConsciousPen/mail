package com.base;


import com.ApplicationManager;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.Optional;


public class TestBase {
    protected ApplicationManager app;

    @BeforeClass
    @Parameters({"configFile"})
    public void setUp(@org.testng.annotations.Optional String configFile) throws Exception {
        if (configFile == null) {
            configFile = System.getProperty("configFile");
        }
        if (configFile == null) {
            configFile = System.getenv("configFile");
        }
        if (configFile == null) {
            configFile = "application.properties";
        }
        // set default properties file
        Properties properties = new Properties();
        properties.load(new FileReader(new File(configFile)));
        app = new ApplicationManager(properties);
        app.getFTPHelper().installConfig();
        app.setProperties(properties);
    }

    public static String generateRandomString() {
        Random rnd = new Random();
        if (rnd.nextInt(3) == 0) {
            return "";
        } else {
            return "tests.test" + rnd.nextInt();
        }
    }

    @AfterTest
    public void tearDown() throws Exception {
        app.stop();
    }

}
