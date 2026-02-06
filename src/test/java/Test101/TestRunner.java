package Test101;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "Features",
        glue = "Test101.StepDefs",
        plugin = {
        "pretty",
        "json:target/cucumber-reports/cucumber.json", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)

public class TestRunner{

}
