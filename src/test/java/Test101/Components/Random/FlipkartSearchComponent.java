package Test101.Components.Random;

import Test101.Components.Common;
import Test101.Components.Element;
import base.RunLog;
import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FlipkartSearchComponent extends Common {

    String locatorsPageName = "FlipkartHomePage";

    @FindBy
    private By searchBar;
    private By searchSuggestions;
    private By relevanceElementTxt;
    private By navBarMenuItems;

    public FlipkartSearchComponent() {
        try {
            assignLocatorPage();
        } catch (Exception e) {
            RunLog.error("Exception: " + e.getMessage());
        }
    }

    public void assignLocatorPage() {
        searchBar = Element.returnBrowserElementFromRepo(locatorsPageName, "FlipkartSearchComponent", "searchBar");
        searchSuggestions = Element.returnBrowserElementFromRepo(locatorsPageName, "FlipkartSearchComponent", "searchSuggestions");
        relevanceElementTxt = Element.returnBrowserElementFromRepo(locatorsPageName, "FlipkartSearchComponent", "relevanceElement");
        navBarMenuItems = Element.returnBrowserElementFromRepo(locatorsPageName, "FlipkartNavBarComponent", "navBarMenuItems");
    }

    public void searchOnFlipkart(String searchText) {
        try {
            WebElement searchBox = driver.findElement(searchBar);
            typeAsCharacters(searchText, searchBox);
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            RunLog.info("Searching for: " + searchText);
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Failed to search on Flipkart" , "searchOnFlipkart()", e.getMessage());
        }
    }

    public void selectSuggestion(String suggestion) {
        try {
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(3));
            int nthSuggestionIndex = Integer.parseInt(suggestion);
            // Find the <ul> element
            WebElement suggestionsList = driver.findElement(searchSuggestions);
            // Find all <li> elements within the <ul> element
            List<WebElement> suggestionItems = suggestionsList.findElements(By.tagName("li"));
            // Check if the nth suggestion exists
            if (nthSuggestionIndex >= 0 && nthSuggestionIndex < suggestionItems.size()) {
                // Get the nth suggestion item
                WebElement nthSuggestion = suggestionItems.get(nthSuggestionIndex);
                // Find the <a> element within the suggestion item
                WebElement suggestionLink = nthSuggestion.findElement(By.tagName("a"));
                // Click on the <a> element
                suggestionLink.click();
                RunLog.info("Selected suggestion: " + suggestion + " from Search Suggestions");
                Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(10));
            } else {
                RunLog.info("Invalid suggestion index.");
            }
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Failed to select Search Suggestion on Flipkart" , "selectSuggestion()", e.getMessage());
        }
    }

    public void validateSearchResultIsDisplayed(String screenshotName) {
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Wait for 10 seconds
            WebElement relevanceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(relevanceElementTxt));
            RunLog.info("Search results are displayed");
            takeScreenshot(screenshotName);
            attachScreenShotOfThePageToAllureReport(screenshotName);
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Failed to validate search result", "validateSearchResultIsDisplayed()", e.getMessage());
        }
    }

    public void getLinksFromNavigationBar() {
        try {
            // Find all span elements within the navigation bar
            RunLog.info("Getting all the elements from navigation bar");
            List<WebElement> navBarItems = driver.findElements(navBarMenuItems);
            RunLog.specialInfo("Menu Items from Navigation Bar: ");
            // Print the text content of each span element
            for (WebElement item : navBarItems) {
                RunLog.info(item.getText());
            }
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Failed to validate search result", "validateSearchResultIsDisplayed()", e.getMessage());
        }
    }

}
