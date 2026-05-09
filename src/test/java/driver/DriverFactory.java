package driver;

import config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/*
 * WebDriver lifecycle manager.
 *
 * Key design choice: use ThreadLocal so parallel execution does not share a single driver instance.
 * Each scenario thread gets its own browser session; teardown must call quitDriver() to prevent leaks.
 *
 * Browser selection and headless behavior come from ConfigReader (browser/headless).
 */
public class DriverFactory {

    /*
     * Thread-confined WebDriver storage.
     * Always remove() in quitDriver() so one thread cannot accidentally reuse a previous scenario's driver.
     */
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static void initDriver() {
        if (DRIVER.get() != null) {
            return;
        }

        ConfigReader config = ConfigReader.getInstance();
        String browser = config.getBrowser();
        boolean headless = config.isHeadless();

        WebDriver driver;
        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    // Prefer the new headless implementation when available (Selenium/Chrome modern default).
                    chromeOptions.addArguments("--headless=new");
                }
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("-headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();
        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            // Fail fast: callers should initialize in hooks before accessing pages/actions.
            throw new IllegalStateException("WebDriver is not initialized. Call DriverFactory.initDriver() first.");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        try {
            if (driver != null) {
                driver.quit();
            }
        } finally {
            // Always clear ThreadLocal, even if quit() throws, to avoid memory/thread retention.
            DRIVER.remove();
        }
    }

}
