package Test101.StepDefs;

import Test101.Factory.PageFactory.MyPageFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;

public class RandomStepDef extends MyPageFactory {

    @When("Search for {string}")
    @Step("Search for Search Text")
    public void createNewAccountUsingAPI(String searchText) {
        flipkartHomePage().flipkartSearchComponent().searchOnFlipkart(searchText);
    }

    @Then("Select {string} from the suggestion list")
    @Step("Select suggestion from the suggestion list")
    public void selectFromTheSuggestionList(String suggestion) {
        flipkartHomePage().flipkartSearchComponent().selectSuggestion(suggestion);
    }

    @And("Validate search result is displayed and save screenshot with {string}")
    @Step("Validate search result is displayed and save screenshot with ScreenshotName")
    public void validateSearchResultIsDisplayed(String screenshotName) {
        flipkartHomePage().flipkartSearchComponent().validateSearchResultIsDisplayed(screenshotName);
    }

    @Then("User gets all the links form the navigation bar")
    public void userGetsAllTheLinksFormTheNavigationBar() {
        flipkartHomePage().flipkartSearchComponent().getLinksFromNavigationBar();
    }
}
