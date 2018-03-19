package com.base;

import com.ApplicationManager;
import com.User;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public abstract class HelperBase {

    protected ApplicationManager manager;

    public HelperBase(ApplicationManager manager) {
        this.manager = manager;
    }


}