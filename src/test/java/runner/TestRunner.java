package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/*
 * Cucumber runner entry point for TestNG execution.
 *
 * Note: the features path is currently filesystem-relative. If you intend to run this from
 * different working directories or in CI, consider switching to a classpath-based path later.
 */
@CucumberOptions(
        features = "classpath:features",
        glue = {"steps", "hooks"},
        plugin = {"pretty", "summary", "html:target/cucumber-reports/report.html", "json:target/cucumber.json"},
        tags = "@buzz"
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
