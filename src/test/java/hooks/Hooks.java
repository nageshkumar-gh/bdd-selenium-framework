package hooks;

import actions.LoginAction;
import config.ConfigReader;
import driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;

/*
 * Cucumber scenario lifecycle hooks.
 *
 * Keep setup/teardown consistent for every scenario:
 * - create a clean WebDriver session for the current thread
 * - apply the configured timeout
 * - navigate to the entry page for the feature under test
 */
public class Hooks {

    ConfigReader config = ConfigReader.getInstance();

    @Before(order = 1)
    public void launchBrowser() {
        System.out.println("Launching Browser");
        DriverFactory.initDriver();
    }
    @Before(order = 2)
    public void openLoginPage() {
        System.out.println("Opening Login Page");
        DriverFactory.getDriver().get(config.getBaseUrl());
    }
    @Before(order = 3,value = "not @auth")
    public void login(){
        System.out.println("Logging in...");
        LoginAction loginAction=new LoginAction();
        loginAction.login("Admin", "admin123");
    }
    @After
    public void after(){
        // Always quit via DriverFactory so ThreadLocal cleanup is guaranteed.
        DriverFactory.quitDriver();
    }
}
