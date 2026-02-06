package Test101.Components;

import base.Environment;
import base.LaunchBrowser;
import base.RunLog;
import base.TestAsserts;
import com.google.common.util.concurrent.Uninterruptibles;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Common extends Element {

    public static WebDriver driver;
    public WebDriverWait webDriverWait;
    public JavascriptExecutor jse = (JavascriptExecutor) driver;
    public Environment environment = new Environment();
    private static final int STANDARD_PAGE_TIMEOUT_SECONDS = 120;
    private static final String SCREENSHOT_PATH = "Artifacts/screenshots/";

    public void setUp(){
        clearLogFile("Artifacts/logs/TestLogs.log");
        RunLog.specialInfo("The test execution for this scenario is starting.");
        String osVersion = System.getProperty("os.version");
        RunLog.info("Operating System Version: " + osVersion);
        String javaVersion = System.getProperty("java.version");
        RunLog.info("Java Version: " + javaVersion);
    }

    public void navigateToApplicationInBrowser(String Application, String browser) {
        try{
            String url = environment.getValue(Application);
            RunLog.info("Launching Browser: " + browser);
            LaunchBrowser launchBrowser = new LaunchBrowser(browser);
            launchBrowser.getUrl(url);
            RunLog.info("Navigating to URL: " + url);
            driver = launchBrowser.getDriver();
            Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);
            attachScreenShotOfThePageToAllureReport(Application + " website in " + browser + " browser");
        } catch (Exception e){
            RunLog.error("Not able to launch browser " + e);
        }
    }

    public void navigateToURLInBrowser(String url, String browser) {
        try{
            String website = "https://" + url;
            RunLog.info("Launching Browser: " + browser);
            LaunchBrowser launchBrowser = new LaunchBrowser(browser);
            launchBrowser.getUrl(website);
            RunLog.info("Navigating to URL: " + url);
            driver = launchBrowser.getDriver();
            waitForPageLoad(driver);
            Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
            attachScreenShotOfThePageToAllureReport(  "URL in " + browser + " browser");
        } catch (Exception e){
            RunLog.error("Not able to launch browser " + e);
        }
    }

    public void navigateToWebsite(String websiteName) {
        try{
            String site = environment.getValue(websiteName);
            RunLog.info("Navigating to URL " + site);
            driver.get(site);
            driver.manage().window().maximize();
            RunLog.info("Navigated to " + site);
            Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);
        } catch (Exception e){
            RunLog.error("Failed to navigate to the website: " + websiteName);
            RunLog.error("Please verify the website name and its corresponding value in the environment.yaml file.");
            RunLog.error("Exception: " + e.getMessage());
        }
    }

    public void navigateToURL(String URL) {
        try{
            driver.get(URL);
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
            String currentUrl = driver.getCurrentUrl();
            if (URL.contains(currentUrl)){
                RunLog.info("Navigated to the URL");
                RunLog.info("Current URL: " + currentUrl);
            } else {
                RunLog.warn("Navigation status: Uncertain. May or may not have navigated.");
            }
        } catch (Exception e){
            RunLog.error("Not able to navigate to the URL. Exception: " + e.getMessage());
        }
    }

    public void navigateToSalesforceLoginUrl(String salesforceURL) {
        try{
            driver.get(salesforceURL);
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
            String currentUrl = driver.getCurrentUrl();
            String salesforceUrl = "playful-bear-tflz0-dev-ed.trailblaze.lightning.force.com";
            if (currentUrl.contains(salesforceUrl)){
                RunLog.info("Successfully navigated to the Salesforce Login URL");
                RunLog.info("Current URL: "+ currentUrl);
            } else {
                RunLog.warn("Navigation status: Uncertain. May or may not have navigated.");
            }
        } catch (Exception e){
            RunLog.error("Not able to navigate to the URL. Exception: " + e.getMessage());
        }
    }

    public void closeAndQuitBrowser() {
        try {
            // Close the browser and release resources
            RunLog.info("Closing all tabs");
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
                RunLog.info("Closing the browser");
                driver.quit();
                Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
            }
            RunLog.info("Browser has been closed");
        } catch (Exception e) {
            RunLog.error("An error occurred while closing the browser: " + e.getMessage());
        }
    }

    public void tearDownForTestFailure(Scenario scenario, Temporal testStartTime){
        // Capture screenshot on test failure
        RunLog.info("A test failure has been detected.");
        captureScreenshot(scenario);
        RunLog.info("Test execution is finished. Verifying for active browser session");
        if (driver != null) {
            RunLog.info("The browser session remains active; therefore, it will be closed now.");
            driver.quit();
            RunLog.info("The browser has been successfully closed.");
        }else {
            RunLog.info("There is no active browser session.");
        }
        addAdditionalInfoToAllureReport(testStartTime);
        RunLog.info("Test completed for this scenario. Please refer to the reports for detailed results.");
    }

    public void tearDown(Temporal testStartTime){
        RunLog.specialInfo("All tests passed successfully.");
        RunLog.info("Browser session has been properly closed.");
        addAdditionalInfoToAllureReport(testStartTime);
        attachLogsToAllureReport();
        RunLog.info("Test completed for this scenario. Please refer to the reports for detailed results.");
    }

    public void addAdditionalInfoToAllureReport(Temporal testStartTime) {
        try {
            LocalDateTime testEndTime = LocalDateTime.now();
            Duration duration = Duration.between(testStartTime, testEndTime);
            long secondsTaken = duration.getSeconds();
            // Format date and time
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH 'hours' mm 'minutes'");
            String formattedDate = testEndTime.format(dateFormatter);
            String formattedTime = testEndTime.format(timeFormatter);

            // Log test completion details
            RunLog.info("Test completed on " + formattedDate + " at " + formattedTime + " IST");
            RunLog.info("Time taken: " + secondsTaken + " seconds");

            // Create the additional information string
            String additionalInfo = "Test completed on " + formattedDate + " at " + formattedTime + " IST"
                    + "\nTime taken: " + secondsTaken + " seconds";

            // Add additional information to Allure report
            Allure.addAttachment("Additional Info", "text/plain", additionalInfo);
        } catch (Exception e) {
            RunLog.error("Error updating additional info to Allure Report: " + e.getMessage());
        }
    }

    public void captureScreenshot(Scenario scenario) {
        if (driver instanceof TakesScreenshot) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            RunLog.info("Capturing screenshot");
            scenario.attach(screenshot, "image/png", "Screenshot");
        }
    }

    public static void waitForElementToBeVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            RunLog.info("Element is visible: " + locator);
        } catch (TimeoutException e) {
            throw new TimeoutException("Element not found or not visible within 60 seconds: " + locator);
        }
    }

    public void clearAndSendKeys(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
        RunLog.info("Text Entered: " + value);
    }

    public void typeAsCharacters(String value, WebElement element) {
        for (char ch : value.toCharArray()) {
            Uninterruptibles.sleepUninterruptibly(60, TimeUnit.MILLISECONDS);
            element.sendKeys(ch + "");
        }
    }

    public void scrollToBottomOfThePage() {
        jse.executeScript("window.scrollTo(0, window.innerHeight)");
    }

    public void scrollToEndOfThePage() {
        jse.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public static void takeScreenshot(String screenshotName) {
        // Take the screenshot
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Save the screenshot to the specified path
        String screenshotPath = SCREENSHOT_PATH + screenshotName + ".png";
        try {
            FileHandler.copy(screenshotFile, new File(screenshotPath));
            RunLog.info("Screenshot saved to: " + screenshotPath);
        } catch (IOException e) {
            RunLog.error("Not able to take screenshot. " + e.getMessage());
        }
    }

    public static void actionClick(WebElement elementToBeClicked) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(elementToBeClicked).click().build().perform();
        } catch (Exception var2) {
            TestAsserts.fail("Could not click on element - " + elementToBeClicked, var2);
        }

    }

    public static void clickElementWithJS(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    Map<String, WebElement> ElementMapWithArg;

    @SuppressWarnings("unchecked")
    public Map<String, WebElement> getElementsAsMap(String jsPath) {
        String jScript = readFileAsString(jsPath);
        wait(120).until(d -> {
            ElementMapWithArg = ((Map<String, WebElement>) jse.executeScript(jScript));
            return !ElementMapWithArg.isEmpty();
        });
        return ElementMapWithArg;
    }

    @SuppressWarnings("unchecked")
    Function<String, Map<String, WebElement>> elementMap = (script) -> ((Map<String, WebElement>) jse.executeScript(script));

    public Map<String, WebElement> getElementsAsMap(String jsPath, String labelShouldPresent) {
        wait(120).until(d -> elementMap.apply(readFileAsString(jsPath)).containsKey(labelShouldPresent));
        return elementMap.apply(readFileAsString(jsPath));
    }

    public Wait<WebDriver> wait(int waitForSeconds) {
        try {
            return new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(waitForSeconds))
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(NoSuchElementException.class, JavascriptException.class)
                    .ignoring(StaleElementReferenceException.class);
        } catch (AssertionError | Exception e) {
            TestAsserts.fail("Failed in fluent wait while waiting javascript / web element return: " + e.getMessage());
            return null;
        }
    }

    public String readFileAsString(String filePath) {
        String content = "";
        try {
            filePath = "src/test/resources/" + filePath;
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            RunLog.error("Not able to read file " + e.getMessage());
        }
        return content;
    }

    public static void clickElementJS(WebElement element) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].click();", new Object[]{element});
        } catch (Exception var3) {
            TestAsserts.fail("Unable to click element using JS  - " + element, var3);
        }
    }

    public static void waitForPageLoad(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(STANDARD_PAGE_TIMEOUT_SECONDS));
            wait.until((driver1) -> {
                return String.valueOf(((JavascriptExecutor) driver1).executeScript("return document.readyState;", new Object[0])).equals("complete");
            });
            RunLog.info("PAGE LOAD COMPLETED");
        } catch (Exception e) {
            RunLog.error("Failed to wait for page load", e);
        }
    }

    public static String getDefaultTextFromDropDown(WebDriver driver, By inputElement) {
        String runningDriver = "";
        String selectedText = "";
        try {
            Select select = new Select(driver.findElement(inputElement));
            selectedText = select.getFirstSelectedOption().getText();
            return selectedText;
        } catch (Exception var4) {
            TestAsserts.fail(runningDriver + "Error occurred while getting the text -" + inputElement, var4);
            return selectedText;
        }
    }

    public static void scrollToAnElement(WebDriver driver, WebElement inputElement) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", new Object[]{inputElement});
        } catch (Exception var2) {
            TestAsserts.fail("Unable to scroll to element  - " + inputElement, var2);
        }
    }

    public static void scrollToAnElement(WebDriver driver, By inputBy) {
        try {
            WebElement inputElement = driver.findElement(inputBy);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", new Object[]{inputElement});
        } catch (Exception var3) {
            TestAsserts.fail("Unable to scroll to element  - " + inputBy.toString(), var3);
        }
    }

    public static void switchToDefaultWindowContentFromFrame(WebDriver driver) {
        try {
            driver.switchTo().defaultContent();
        } catch (Exception var1) {
            TestAsserts.fail("Unable to switch to default content window from Frame. ", var1);
        }
    }

    public static void doubleClickOnElement(WebDriver driver, WebElement inputElement) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(inputElement).doubleClick().build().perform();
        } catch (Exception var3) {
            TestAsserts.fail("Unable to double click on element  -   " + inputElement, var3);
        }
    }

    public static void actionClick(WebDriver driver, WebElement elementToBeClicked) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(elementToBeClicked).click().build().perform();
        } catch (Exception var2) {
            TestAsserts.fail("Could not click on element - " + elementToBeClicked, var2);
        }
    }

    public static void acceptAlert() {
        try {
            driver.switchTo().alert().accept();
        } catch (Exception var1) {
            TestAsserts.fail("Unable too accept alert!", var1);
        }
    }

    public static void switchToFrameUsingFrameName(String frameName) {
        try {
            driver.switchTo().frame(frameName);
        } catch (Exception var2) {
            TestAsserts.fail("Failed to switch to frame  - " + frameName, var2);
        }
    }

    public static void mouseHover(WebElement inputElement) {
        try {
            String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject){ arguments[0].fireEvent('onmouseover');}";
            JavascriptExecutor js = (JavascriptExecutor)driver;
            js.executeScript(mouseOverScript, new Object[]{inputElement});
        } catch (Exception var3) {
            TestAsserts.fail("Failed to move hover over the element  - " + inputElement, var3);
        }
    }

    public void hoverOnElementAndClick(WebElement elementToHoverOn) {
        try {
            Action hoverAndClick = new Actions (driver)
                    .moveToElement(elementToHoverOn)
                    .click()
                    .build();
            hoverAndClick.perform();
            RunLog.info("Clicked on element after hover");
        } catch (Exception e) {
            logErrorAndTakeScreenshot("This may be due to element was not found or unable to perform action on the element", "hoverOnElementAndClick()", e.getMessage());
        }
    }

    public void refreshAndWait() {
        try {
            driver.navigate().refresh();
            waitForPageLoad(driver);
        } catch (AssertionError | Exception e) {
            logErrorAndTakeScreenshot("Failed to refresh the page", "refreshAndWait()", e.getMessage());
        }
    }

    public Boolean isAreaExpanded(WebElement webElement) {
        return this.webDriverWait.until(ExpectedConditions.attributeToBe(webElement, "aria-expanded", "true"));
    }

    public void logErrorAndTakeScreenshot(String errorMessageToLog, String currentMethodName, String exception) {
        updateErrorDetailsToReport(errorMessageToLog, currentMethodName, exception);
        attachScreenShotOfThePageToAllureReport("Error Screenshot");
        TestAsserts.fail(errorMessageToLog + "\n" + exception);
    }

    public static void updateErrorDetailsToReport(String message, String methodName, String exception) {
        try {
            RunLog.error(message);
            RunLog.error("Failed in method: " + methodName);
            RunLog.error("Exception: " + exception);
            String descriptionForAllureReport = message + "\nFailed in method: " + methodName;
            Allure.addAttachment("Error Description", "text/plain", descriptionForAllureReport);
        } catch (Exception var4) {
            RunLog.error("The error logging updateErrorDetailsToReport method failed");
        }
    }

    public static void attachScreenShotOfThePageToAllureReport(String screenshotName) {
        try {
            // Take a screenshot and convert it directly to bytes
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            ByteArrayInputStream screenshot = new ByteArrayInputStream(screenshotBytes);
            RunLog.info("Attaching Screenshot to Allure Report");
            Allure.addAttachment(screenshotName, screenshot);
        } catch (Exception e) {
            RunLog.error("Issue during snapshot capture for Allure Report: ", e);
        }
    }

    public static String getLogs() {
        String filePath = "Artifacts/logs/TestLogs.log";
        try {
            byte[] logsBytes = Files.readAllBytes(Paths.get(filePath));
            return new String(logsBytes);
        } catch (IOException e) {
            RunLog.error("Error reading logs. Exception: " + e.getMessage());
            return "Error reading logs";
        }
    }

    public static void attachLogsToAllureReport() {
        // Retrieve logs from RunLog class
        String logs = getLogs();
        // Attach logs to Allure report
        Allure.addAttachment("Test Logs", "text/plain", logs);
    }

    public static void clearLogFile(String logFilePath) {
        try {
            RandomAccessFile file = new RandomAccessFile(logFilePath, "rw");
            file.setLength(0);
            file.close();
        } catch (Exception e) {
            RunLog.error("Not able to clear the Log File. Exception: " + e.getMessage());
        }
    }

    Function<String, Map<String, List<WebElement>>> dropDownsElementMap = (script) -> ((Map<String, List<WebElement>>) jse.executeScript(script));

    public Map<String, List<WebElement>> getDropDownsElementMap(String jsPath, String labelName, String valueToBeSelected) {
        wait(120).until(d -> dropDownsElementMap.apply(readFileAsString(jsPath)).containsKey(labelName));
        Map<String, List<WebElement>> elementsDropDown = dropDownsElementMap.apply(readFileAsString(jsPath));
        elementsDropDown.get(labelName).forEach(element -> {
            if (element.getAttribute("title").contains(valueToBeSelected)) {
                clickElementJS(element);
                Uninterruptibles.sleepUninterruptibly(Duration.ofMillis(500));
                RunLog.info("Drop down Selected: " + valueToBeSelected);
            }
        });
        return elementsDropDown;
    }

    public Map<String, List<WebElement>> getDropDownsElementMapWithGetText(String jsPath, String labelName, String valueToBeSelected) {
        wait(120).until(d -> dropDownsElementMap.apply(readFileAsString(jsPath)).containsKey(labelName));
        Map<String, List<WebElement>> elementsDropDown = dropDownsElementMap.apply(readFileAsString(jsPath));
        elementsDropDown.get(labelName).forEach(element -> {
            if (element.getText().contains(valueToBeSelected)) {
                element.click();
                Uninterruptibles.sleepUninterruptibly(Duration.ofMillis(500));
                RunLog.info("Drop down Selected: " + valueToBeSelected);
            }
        });
        return elementsDropDown;
    }

    public void selectOptionFromDropdown(String jsPath, String dropdownLabel, int indexToBeSelected) {
        wait(120).until(d -> dropDownsElementMap.apply(readFileAsString(jsPath)).containsKey(dropdownLabel));
        Map<String, List<WebElement>> elementsDropDown = dropDownsElementMap.apply(readFileAsString(jsPath));
        // Get the list of options for the specified dropdown label
        List<WebElement> options = elementsDropDown.get(dropdownLabel);
        if (options == null) {
            RunLog.error("Dropdown not found: " + dropdownLabel);
            return;
        }
        if (indexToBeSelected >= 0 && indexToBeSelected < options.size()) {
            // Click the parent select element to open the dropdown
            options.get(indexToBeSelected).click();
            RunLog.info("Dropdown option selected at index: " + indexToBeSelected);
        } else {
            RunLog.error("Invalid index for dropdown '" + dropdownLabel + "': " + indexToBeSelected);
        }
    }

    public void selectOptionFromDropdown(String jsPath, String dropdownLabel, String valueToBeSelected) {
        wait(60).until(d -> dropDownsElementMap.apply(readFileAsString(jsPath)).containsKey(dropdownLabel));
        Map<String, List<WebElement>> elementsDropDown = dropDownsElementMap.apply(readFileAsString(jsPath));
        // Get the list of options for the specified dropdown label
        List<WebElement> options = elementsDropDown.get(dropdownLabel);
        if (options == null) {
            RunLog.error("Dropdown not found: " + dropdownLabel);
            return;
        }
        WebElement optionToSelect = null;
        for (WebElement option : options) {
            if (option.getText().equals(valueToBeSelected)) {
                optionToSelect = option;
                break;
            }
        }
        if (optionToSelect != null) {
            optionToSelect.click();
            RunLog.info("Dropdown option selected: " + valueToBeSelected);
        } else {
            RunLog.error("Option not found in dropdown '" + dropdownLabel + "': " + valueToBeSelected);
        }
    }


    // ... add other common methods here ...

}
