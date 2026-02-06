package Test101.StepDefs;

import Test101.Factory.PageFactory.MyPageFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.qameta.allure.Step;
import java.time.LocalDateTime;


public class CommonStepDef extends MyPageFactory {
    private LocalDateTime testStartTime;

    @Given("User launch {string} in {string}")
    @Step("User launched website in browser")
    public void userLaunchIn(String website, String browser){
        commonPage().launchApplication(website, browser);
    }

    @Given("User launch {string} and navigate to {string}")
    @Step("User launch browser and navigate to URL")
    public void userLaunchBrowserAndNavigateToUrl(String browser, String url){
        commonPage().navigateToURL(url, browser);
    }

    @And("User navigate to {string}")
    @Step("User navigate to URL")
    public void navigateToURL(String url) {
        commonPage().navigateToWebsite(url);
    }


    @Then("Close the browser")
    @Step("User Closed the browser")
    public void closeTheBrowser() {
        commonPage().quitBrowser();
    }

    @Before
    public void setUp() {
        testStartTime = LocalDateTime.now(); // Capturing the test start time
        commonPage().setUp();
    }
    
    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            commonPage().tearDownForTestFailure(scenario, testStartTime);
        } else {
            commonPage().tearDown(testStartTime);
        }
    }

}
