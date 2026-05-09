package steps;

import actions.LoginAction;
import io.cucumber.java.en.*;
import org.testng.Assert;

/*
 * Step definitions for login feature scenarios.
 *
 * Guideline:
 * - Keep assertions and scenario intent in steps
 * - Keep UI mechanics in the action/page layers
 */
public class LoginStep {

    // Stateless action object; constructed once per step-definition instance.
    private final LoginAction loginAction=new LoginAction();

    @Given("the user is on the OrangeHRM login page")
    public void the_user_is_on_the_orange_hrm_login_page() {
        // Lightweight guard to ensure Hooks navigated to the expected entry page.
        Assert.assertTrue(loginAction.getCurrentUrl().contains("auth/login"));
    }

    @When("the user logs in with username {string} and password {string}")
    public void the_user_logs_in_with_username_and_password(String username, String password ) throws InterruptedException {
        loginAction.login(username, password);


    }

    @Then("the user should be logged in successfully")
    public void the_user_should_be_logged_in_successfully() {
        Assert.assertTrue(loginAction.getCurrentUrl().contains("dashboard"));
    }

    @Then("an authentication error message should be displayed")
    public void an_authentication_error_message_should_be_displayed() {
        // These are stable application messages; if AUT text changes, update expected values here.
        Assert.assertEquals(loginAction.getInvalidLoginMsg(), "Invalid credentials");
    }

    @Then("a required field validation message should be displayed")
    public void a_required_field_validation_message_should_be_displayed() {
        Assert.assertEquals(loginAction.getMissingCredMsg(), "Required");
    }


}
