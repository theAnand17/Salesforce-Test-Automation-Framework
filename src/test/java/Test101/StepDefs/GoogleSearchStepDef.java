package Test101.StepDefs;

import Test101.Factory.PageFactory.MyPageFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;

public class GoogleSearchStepDef extends MyPageFactory {

    @When("User search for {string}")
    @Step("User search for Name")
    public void searchGoogle(String searchText){
        googleHomePage().googleSearch(searchText);
    }

    @Given("User gets the search result and save screenshot with {string}")
    @Step("User gets the search result and save screenshot")
    public void validateSearchResult(String screenshotName){
        googleHomePage().validateSearchResult(screenshotName);
    }
}
