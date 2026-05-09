package actions;

import driver.DriverFactory;
import pages.LoginPage;

/*
 * Action layer for login.
 *
 * Keeps step definitions focused on business intent by placing UI interaction sequences here.
 * This is a good place to add waiting/retry logic (instead of Thread.sleep) as the framework evolves.
 */
public class LoginAction {

    private LoginPage loginPage;

    public LoginAction() {
        loginPage = new LoginPage();
    }

    public void login(String username, String password) throws InterruptedException {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();

    }
    public String getCurrentUrl(){
        return DriverFactory.getDriver().getCurrentUrl();
    }

    public String getInvalidLoginMsg(){
        return loginPage.getAuthErrorMessage();
    }
    public String getMissingCredMsg(){
        return loginPage.getRequiredFieldValidationMessage();
    }


}
