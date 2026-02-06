package Test101.Components.SFDC;

import Test101.Components.*;
import base.RunLog;
import base.TestAsserts;
import com.google.common.util.concurrent.Uninterruptibles;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class CreateAccount extends Common {

    public static String pathForJs="jsFiles/";
    public static String locatorsPageName = "SFDCLocators";

    @FindBy
    private By btnSave;

    public CreateAccount() {
        try {
            assignLocatorPage();
            this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(90));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void assignLocatorPage() {
        btnSave = Element.returnBrowserElementFromRepo(locatorsPageName, "SFDCCreateRecordComponent", "btnSave");
    }

    public void navigateToNewAccountPage() {
        try {
            try {
                Map<String, WebElement> quickLinks = getElementsAsMap(pathForJs + "subMenu.js", "Accounts");
                clickElementJS(quickLinks.get("Accounts"));
                Map<String, WebElement> subMenuOptions = getElementsAsMap(pathForJs + "subMenuOne.js", "New Account");
                clickElementJS(subMenuOptions.get("New Account"));
            } catch (Exception e) {
                Map<String, WebElement> quickLinks = getElementsAsMap(pathForJs + "subMenu.js", "Accounts");
                clickElementJS(quickLinks.get("Accounts"));
                Map<String, WebElement> subMenuOptions = getElementsAsMap(pathForJs + "subMenuOne.js", "New Account");
                clickElementJS(subMenuOptions.get("New Account"));
            }
        } catch (AssertionError | Exception e) {
            logErrorAndTakeScreenshot("Failed at navigation to New Account from Accounts Menu ", "navigateToNewAccountPage()", e.getMessage());
        }
    }

    public void createAccountRecord(Map<String, String> accountData) {
        try {
            waitForElementToBeVisible(btnSave);
            RunLog.info("New Account Form is displayed");
            attachScreenShotOfThePageToAllureReport("New Account Form in Salesforce");
            Map<String, WebElement> quickLinks = getElementsAsMap(pathForJs + "createRecord.js", "*Account Name");

            clearAndSendKeys(quickLinks.get("*Account Name"), accountData.get("AccountName"));
            clearAndSendKeys(quickLinks.get("Account Number"), accountData.get("AccountNumber"));
            clearAndSendKeys(quickLinks.get("Phone"), accountData.get("PhoneNumber"));
            clearAndSendKeys(quickLinks.get("Billing Street"), accountData.get("StreetAddress"));
            clearAndSendKeys(quickLinks.get("Billing City"), accountData.get("City"));
            clearAndSendKeys(quickLinks.get("Billing Zip/Postal Code"), accountData.get("ZipCode"));
            clearAndSendKeys(quickLinks.get("Billing State/Province"), accountData.get("State"));
            clearAndSendKeys(quickLinks.get("Billing Country"), accountData.get("Country"));
            clearAndSendKeys(quickLinks.get("Shipping Street"), accountData.get("StreetAddress"));
            clearAndSendKeys(quickLinks.get("Shipping City"), accountData.get("City"));
            clearAndSendKeys(quickLinks.get("Shipping Zip/Postal Code"), accountData.get("ZipCode"));
            clearAndSendKeys(quickLinks.get("Shipping State/Province"), accountData.get("State"));
            clearAndSendKeys(quickLinks.get("Shipping Country"), accountData.get("Country"));
            clearAndSendKeys(quickLinks.get("Description"), "Created using automation");

            String Industry = "Industry";
            clickElementJS(quickLinks.get(Industry));
            if (isAreaExpanded(quickLinks.get(Industry))) {
                selectValueFor(Industry, accountData.get("Industry"));
            }

            String Ownership = "Ownership";
            clickElementJS(quickLinks.get(Ownership));
            if (isAreaExpanded(quickLinks.get(Ownership))) {
                selectValueFor(Ownership, accountData.get("Ownership"));
            }

            String Type = "Type";
            clickElementJS(quickLinks.get(Type));
            if (isAreaExpanded(quickLinks.get(Type))) {
                selectValueFor(Type, accountData.get("Type"));
            }
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            WebElement saveButton = driver.findElement(btnSave);
            saveButton.click();
            Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);
            RunLog.info("Entered account details and clicked on save");
            attachScreenShotOfThePageToAllureReport("New Account which got created in this step");
        } catch (AssertionError | Exception e) {
            logErrorAndTakeScreenshot("Failed at creation of New Account from Accounts Menu ", "createAccountRecord()", e.getMessage());
        }
    }

    Map<String, List<WebElement>> dropDownsElementMap;
    public void selectValueFor(String labelName, String valueToBeSelected) {
        try {
            String jScript = readFileAsString(pathForJs + "createRecordDropDown.js");
            this.webDriverWait.until(d -> {
                dropDownsElementMap = ((Map<String, List<WebElement>>) getJSExecutor().executeScript(jScript));
                return dropDownsElementMap.size() > 1;
            });
            RunLog.info("Drop Down Elements: " + dropDownsElementMap.toString());
            dropDownsElementMap.get(labelName).forEach(element -> {
                if (element.getAttribute("title").contains(valueToBeSelected)) {
                    clickElementJS(element);
                }
            });
        } catch (AssertionError | Exception e) {
            Assert.fail("Not able to get the drop down value for field:" + labelName + " from account create page, Exception : " + e.toString());
        }
    }

    public void validateNewAccountData(Map<String, String> accountData) {
        try{
            Map<String, WebElement> quickLinks = getElementsAsMap(pathForJs+ "detailsPage.js","Account Name");
            Map<String, WebElement> detailsPageLookups = getElementsAsMap(pathForJs+ "detailsPageLookUps.js","Account Owner");
            String accountOwner = String.valueOf(detailsPageLookups.get("Account Owner"));
            String accountName = quickLinks.get("Account Name").getText();
            String accountNumber = quickLinks.get("Account Number").getText();
            String type = quickLinks.get("Type").getText();
            String ownership = quickLinks.get("Ownership").getText();
            String industry = quickLinks.get("Industry").getText();
            String billingAddress = quickLinks.get("Billing Address").getText();
            String shippingAddress = quickLinks.get("Shipping Address").getText();
            String description = quickLinks.get("Description").getText();

            TestAsserts.assertEquals(accountName, accountData.get("AccountName"));
            RunLog.info("Account Name is: " + accountName);
            TestAsserts.assertEquals(accountNumber, accountData.get("AccountNumber"));
            RunLog.info("Account Number is: " + accountNumber);
            TestAsserts.assertEquals(type, accountData.get("Type"));
            RunLog.info("Account Type is: " + type);
            TestAsserts.assertEquals(ownership, accountData.get("Ownership"));
            RunLog.info("Account Ownership is: " + ownership);
            TestAsserts.assertEquals(industry, accountData.get("Industry"));
            RunLog.info("Account Industry is: " + industry);
            TestAsserts.assertTextContainsIgnoreCase(billingAddress, accountData.get("StreetAddress"));
            RunLog.info("Account Billing Address is: " + billingAddress);
            TestAsserts.assertTextContainsIgnoreCase(shippingAddress, accountData.get("ZipCode"));
            RunLog.info("Account Shipping Address is: " + shippingAddress);
            TestAsserts.assertEquals(accountOwner, "Satyam Anand");
            RunLog.info("Account Owner is: " + accountOwner);
            TestAsserts.assertEquals(description, "Created using automation");
            RunLog.info("Description: " + description);
            RunLog.info("Account Data validation successful. New account has been created.");
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
        } catch (AssertionError | Exception e) {
            logErrorAndTakeScreenshot("Not able to get the validate the new Account Data", "validateNewAccountData()", e.getMessage());
        }
    }

}
