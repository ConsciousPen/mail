package com.base;


import com.ApplicationManager;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileReader;
import java.util.*;


public class TestBase {
    protected ApplicationManager app;
    int checkCounter;
    int checkFrequency;

    @BeforeTest
    public void setUp() throws Exception {
        // set default properties file
        String configFile = System.getProperty("configFile","application.properties");
        Properties properties = new Properties();
        properties.load(new FileReader(new File(configFile)));
        app = new ApplicationManager(properties);
        checkCounter = 0;
        checkFrequency = Integer.parseInt(properties.getProperty("check.frequency", "0"));
    }

    public boolean ttc(){
        checkCounter++;
        if(checkCounter>checkFrequency){
            checkCounter=0;
            return true;

        }else{
            return false;
        }

    }

    @AfterTest
    public void tearDown() throws Exception {
        app.stop();
    }

    public static String generateRandomString() {
        Random rnd = new Random();
        if (rnd.nextInt(3) == 0) {
            return "";
        } else {
            return "tests.test" + rnd.nextInt();
        }
    }




}
