package Test101.Components.SFDC;

import Test101.Components.Common;
import Test101.Components.Element;
import base.RunLog;
import base.TestAsserts;
import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CreateContact extends Common {

    public static String pathForJs="jsFiles/";
    public static String locatorsPageName = "SFDCLocators";

    @FindBy
    private By btnSave;

    public CreateContact() {
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

    public void navigateToNewContactPage() {
        try {
            try {
                Map<String, WebElement> quickLinks = getElementsAsMap(pathForJs + "subMenu.js", "Contacts");
                clickElementJS(quickLinks.get("Contacts"));
                Map<String, WebElement> subMenuOptions = getElementsAsMap(pathForJs + "subMenuOne.js", "New Contact");
                clickElementJS(subMenuOptions.get("New Contact"));
            }catch(Exception e){
                Map<String, WebElement> quickLinks = getElementsAsMap(pathForJs + "subMenu.js", "Contacts");
                clickElementJS(quickLinks.get("Contacts"));
                Map<String, WebElement> subMenuOptions = getElementsAsMap(pathForJs + "subMenuOne.js", "New Contact");
                clickElementJS(subMenuOptions.get("New Contact"));
            }
        } catch (AssertionError | Exception e) {
            logErrorAndTakeScreenshot("Failed at navigation to New Contact from Contacts Menu ", "navigateToNewContactPage()", e.getMessage());
        }
    }

    public void createContactRecord(Map<String, String> accountData, String accountName) {
        try {
            waitForElementToBeVisible(btnSave);
            RunLog.info("New Contact Form is displayed");
            attachScreenShotOfThePageToAllureReport("New Contact Form in Salesforce");
            Map<String, WebElement> quickLinks = getElementsAsMap(pathForJs + "createRecord.js", "*Last Name");

            clearAndSendKeys(quickLinks.get("First Name"), accountData.get("FirstName"));
            clearAndSendKeys(quickLinks.get("*Last Name"), accountData.get("LastName"));
            clearAndSendKeys(quickLinks.get("Phone"), accountData.get("Phone"));
            clearAndSendKeys(quickLinks.get("Mobile"), accountData.get("Mobile"));
            clearAndSendKeys(quickLinks.get("Email"), accountData.get("Email"));
            clearAndSendKeys(quickLinks.get("Description"), "Created using automation");

            enterValueInLookUpFor("Account Name", accountName);
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            attachScreenShotOfThePageToAllureReport("Account Lookup on New Contact Form");

            WebElement saveButton = driver.findElement(btnSave);
            saveButton.click();
            Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);
            RunLog.info("Entered contact details and clicked on save");
        } catch (AssertionError | Exception e) {
            logErrorAndTakeScreenshot("Failed at creation of New Contact from Contacts Menu ", "navigateToNewContactPage()", e.getMessage());
        }
    }

    public Map<String, WebElement> getFieldsForInput(){
        return getElementsAsMap(pathForJs+"createRecord.js");
    }
    public Map<String, WebElement> getFieldForInput(String fieldName) {
        return getElementsAsMap(pathForJs+"createRecord.js", fieldName);
    }
    public void enterValueInLookUpFor(String fieldName, String value) {
        Map<String, WebElement> fields=getFieldsForInput();
        WebElement element = fields.get(fieldName);
        for(char ch: value.toCharArray()){
            Uninterruptibles.sleepUninterruptibly(20, TimeUnit.MILLISECONDS);
            element.sendKeys(ch+"");
        }
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        String suggestion=fieldName + " suggestion";
        WebElement suggestedElement=getFieldForInput(suggestion).get(suggestion);
        RunLog.info("Suggested Element: "+suggestedElement.getText());
        clickElementJS(suggestedElement);
    }

    public void validateNewContactData(String accountNameOnContact) {
        try{
            Map<String, WebElement> quickLinks = getElementsAsMap(pathForJs+ "detailsPage.js","Account Name");
            Map<String, WebElement> detailsPageLookups = getElementsAsMap(pathForJs+ "detailsPageLookUps.js");
            String contactOwner = String.valueOf(detailsPageLookups.get("Contact Owner"));
            String accountName = String.valueOf(detailsPageLookups.get("Account Name"));
            String createdBy = String.valueOf(detailsPageLookups.get("Created By"));
            String description = quickLinks.get("Description").getText();
            String adminUser = "Satyam Anand";

            TestAsserts.assertEquals(accountName, accountNameOnContact);
            RunLog.info("Account Name on Contact is: " + accountName);

            TestAsserts.assertEquals(contactOwner, adminUser);
            RunLog.info("Contact Owner: " + contactOwner);

            TestAsserts.assertEquals(createdBy, adminUser);
            RunLog.info("Contact is Created By: " + createdBy);

            TestAsserts.assertEquals(description, "Created using automation");
            RunLog.info("Description: " + description);

            RunLog.info("Contact Data validation successful. New contact has been created on Account: " + accountName);
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
        } catch (AssertionError | Exception e) {
            logErrorAndTakeScreenshot("Not able to get the validate the new Contact Data ", "validateNewContactData()", e.getMessage());
        }
    }

}
