package hooks;

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
    @Before
    public void before(){
        System.out.println("Before Hooks");
        ConfigReader config = ConfigReader.getInstance();
        DriverFactory.initDriver();
        // Use base.url from config and keep the app-specific path in code for clarity.
        DriverFactory.getDriver().get(config.getBaseUrl() + "/web/index.php/auth/login");
    }
    @After
    public void after(){
        // Always quit via DriverFactory so ThreadLocal cleanup is guaranteed.
        DriverFactory.quitDriver();
    }
}
