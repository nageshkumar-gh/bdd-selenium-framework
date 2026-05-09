package bases;

import config.ConfigReader;
import driver.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * Base class for page objects.
 *
 * Provides small, reusable wrappers around common element interactions to keep page objects concise.
 * Intentionally minimal: higher-level waiting/retry logic should live in a dedicated wait utility
 * to avoid hiding timing issues behind every action.
 */
public abstract class BasePage {

    // Protected by inheritance, but kept package-private to avoid leaking into test code directly.
    WebDriver driver;
    WebDriverWait wait;
    ConfigReader configReader = ConfigReader.getInstance();
    public BasePage() {
        // Pages are created after Hooks initializes the driver for the current scenario thread.
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, configReader.getInstance().getWaitTimeout());
    }

    protected void type(By locator, String text) {
        waitForVisibility(locator).sendKeys(text);
        //this.driver.findElement(locator).sendKeys(text);
    }
    protected void click(By locator) {
        waitForVisibility(locator).click();
        //this.driver.findElement(locator).click();
    }
    protected String getText(By locator) {
        return waitForVisibility(locator).getText();
        //return this.driver.findElement(locator).getText();
    }

    // Waits until element is visible on the page.
    protected WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    //Waits until element located by locator is visible.
    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    //Waits until element is clickable.
    protected WebElement waitForClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    //Waits until element located by locator is clickable.
    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    //Waits until element is present in the DOM (not necessarily visible).
    protected WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    //Waits until element is invisible.
    protected boolean waitForInvisibility(WebElement element) {
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    //Waits until element contains expected text.
    protected boolean waitForText(WebElement element, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    //Waits until the page title contains expected text.
    protected boolean waitForTitleContains(String title) {
        return wait.until(ExpectedConditions.titleContains(title));
    }

     //Waits until the URL contains expected text.
    protected boolean waitForUrlContains(String urlFragment) {
        return wait.until(ExpectedConditions.urlContains(urlFragment));
    }

}
