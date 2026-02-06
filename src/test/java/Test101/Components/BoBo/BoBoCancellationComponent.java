package Test101.Components.BoBo;

import Test101.Components.Common;
import Test101.Components.Element;
import base.RunLog;
import base.TestAsserts;
import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.Map;

public class BoBoCancellationComponent extends Common {

    String pathForJs="jsFiles/BoBo/";
    String pathForCommonJs="jsFiles/";
    String locatorsPageName = "SFDCLocators";

    @FindBy
    private By boBoCancellationMsg;

    public BoBoCancellationComponent() {
        try {
            assignLocatorPage();
        } catch (Exception e) {
            RunLog.error(e.getMessage());
        }
    }

    public void assignLocatorPage() {
        boBoCancellationMsg = Element.returnBrowserElementFromRepo(locatorsPageName, "SFDCBoBoCancellation", "boBoCancellationMsg");
    }

        public void selectCancellationButton(){
        try{
            Map<String, WebElement> quickActionButtons = getElementsAsMap(pathForCommonJs + "quickActionButtons.js","Cancel Enrollment");
            clickElementJS(quickActionButtons.get("Cancel Enrollment"));
            RunLog.info("The user has successfully clicked on the 'Cancel Enrollment' button.");
        } catch(Exception e){
            logErrorAndTakeScreenshot("The user is unable to select the 'Cancel Enrollment' button from Quick Actions." , "selectCancellationButton()", e.getMessage());
        }
    }

    public void selectCancellationReason(String cancellationReason) {
        try {
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
            selectOptionFromDropdown(pathForJs + "boBoPicklists.js", "Cancellation Reason", cancellationReason);
            RunLog.info("Successfully selected Cancellation Reason for BoBo Enrollment Cancellation");
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Not able to select BoBo Cancellation Reason", "selectCancellationReason()", e.getMessage());
        }
    }

    public void enterCancellationNotes(String cancellationNotes){
        try{
            Map<String, WebElement> quickActionButtons = getElementsAsMap(pathForJs + "boBoDetails.js","Cancellation Notes");
            clearAndSendKeys(quickActionButtons.get("Cancellation Notes"), cancellationNotes);
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
            RunLog.info("The user has successfully entered Cancellation Notes");
            attachScreenShotOfThePageToAllureReport("Cancellation Details");
            new BoBoEnrollmentComponent().clickNextButtonOnFooter();
        } catch(Exception e){
            logErrorAndTakeScreenshot("The user is unable to enter Cancellation Notes on the BoBo Cancellation Form" , "enterCancellationNotes()", e.getMessage());
        }
    }

    public void validateFlowCompletedMessage(){
        try {
            waitForElementToBeVisible(boBoCancellationMsg);
            WebElement boboMsg = driver.findElement(boBoCancellationMsg);
            TestAsserts.assertCondition(true, boboMsg.isDisplayed(), "BoBo Flow Complete Message is Not Displayed");
            RunLog.specialInfo("Cancellation Flow for BoBo has been successfully completed. Upon approval, the BoBo Enrollment will be Cancelled.");
            attachScreenShotOfThePageToAllureReport("BoBo Flow Complete");
            new BoBoEnrollmentComponent().clickFinishButtonOnFooter();
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Not able to validate BoBo Cancellation Flow Completed Message", "validateFlowCompletedMessage()", e.getMessage());
        }
    }
}
