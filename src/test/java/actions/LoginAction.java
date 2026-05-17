package actions;

import pages.LoginPage;

/*
 * Action layer for login.
 *
 * Keeps step definitions focused on business intent by placing UI interaction sequences here.
 * This is a good place to add waiting/retry logic (instead of Thread.sleep) as the framework evolves.
 */
public class LoginAction {

    private final LoginPage loginPage;

    public LoginAction() {
        loginPage = new LoginPage();
    }

    public void login(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();

    }

    public boolean getInvalidLoginMsg(String InvalidMessage) {
        return loginPage.getAuthErrorMessage(InvalidMessage);
    }
    public boolean getMissingCredMsg(String MissingMessage) {
        return loginPage.getRequiredFieldValidationMessage(MissingMessage);
    }
    public boolean verifyLoginPage(String urlPart)
    {
        return loginPage.isDisplayed(urlPart);
    }

}
