package Test101.Components;

import base.RunLog;
import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class SearchMe extends Common {

    String locatorsPageName = "GoogleHomePage";

    @FindBy
    private By searchBar;
    private By searchButton;
    private By resultStats;
    private By txtAllResults;

    public SearchMe() {
        try {
            assignLocatorPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void assignLocatorPage() {
        searchBar = Element.returnBrowserElementFromRepo(locatorsPageName, "GoogleSearchComponent", "searchBar");
        searchButton = Element.returnBrowserElementFromRepo(locatorsPageName, "GoogleSearchComponent", "searchButton");
        resultStats = Element.returnBrowserElementFromRepo(locatorsPageName, "GoogleSearchComponent", "resultStats");
        txtAllResults = Element.returnBrowserElementFromRepo(locatorsPageName, "GoogleSearchComponent", "txtAllResults");
    }

    public void searchMeOnGoogle(String searchText) {
        try {
            String text = environment.getValue(searchText);
            WebElement searchBox = driver.findElement(searchBar);
            searchBox.sendKeys(text);
            WebElement searchBtn = driver.findElement(searchButton);
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            searchBtn.sendKeys(Keys.ENTER);
            RunLog.info("Searching for: " + text);
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Failed to search on Google " , "searchMeOnGoogle", e.getMessage());
        }
    }

    public void validateSearch(String screenshotName) {
        try{
            waitForElementToBeVisible(txtAllResults);
            takeScreenshot(screenshotName);
            attachScreenShotOfThePageToAllureReport("Google Search Result");
        } catch (Exception e){
            RunLog.error("Not able to Validate search result " + e);
            attachScreenShotOfThePageToAllureReport("Result Stats not visible after Google search");
        }

    }
}
