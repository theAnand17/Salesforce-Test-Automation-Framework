package Test101.Components.BoBo;

import Test101.Components.Common;
import Test101.Components.Element;
import Test101.Components.SFDC.AccountRelatedList;
import Test101.Components.SFDC.Waits;
import Test101.utils.StorageMap;
import base.RunLog;
import base.TestAsserts;
import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.Map;

import static Test101.Components.SFDC.Waits.WAIT_CONDITIONS.CLICKABLE;
import static Test101.Components.SFDC.Waits.waitForElement;

public class BoBoAccountProgram extends Common {

    String pathForJs="jsFiles/BoBo/";
    String pathForCommonJs="jsFiles/";
    String locatorsPageName = "SFDCLocators";

    @FindBy
    private By notificationButton;
    private By notificationTitle;
    private By notificationContent;
    private By notificationTitleContainer;
    private By markAsRead;

    public BoBoAccountProgram() {
        try {
            assignLocatorPage();
        } catch (Exception e) {
            RunLog.error(e.getMessage());
        }
    }

    public void assignLocatorPage() {
        notificationButton = Element.returnBrowserElementFromRepo(locatorsPageName, "NotificationComponent", "notificationButton");
        notificationTitle = Element.returnBrowserElementFromRepo(locatorsPageName, "NotificationComponent", "notificationTitle");
        notificationContent = Element.returnBrowserElementFromRepo(locatorsPageName, "NotificationComponent", "notificationContent");
        notificationTitleContainer = Element.returnBrowserElementFromRepo(locatorsPageName, "NotificationComponent", "notificationTitleContainer");
        markAsRead = Element.returnBrowserElementFromRepo(locatorsPageName, "NotificationComponent", "markAsRead");
    }

    public void navigateToBoBoAccountProgram(String accountId) {
        try {
            refreshAndWait();
            AccountRelatedList accountRelatedlist = new AccountRelatedList();
            accountRelatedlist.navigateToRecordsListView("Account_Programs__r", accountId);
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(3));
            attachScreenShotOfThePageToAllureReport("Account Programs on Account");
            Map<String, WebElement> accountPrograms = getElementsAsMap(pathForJs + "boBoAccountProgram.js");
            clickElementJS(accountPrograms.get("Account Program Name"));
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
            RunLog.info("Navigated to Account Program");
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Unable to navigate to BoBo Account Program", "navigateToBoBoAccountProgram()", e.getMessage());
        }
    }

    public void validateEnrollmentStatusOnBoBoAccountProgram(String status) {
        try{
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
            Map<String, WebElement> accountProgramDetails = getElementsAsMap(pathForCommonJs + "detailsPage.js");
            String preApprovedEnrollmentStatus = accountProgramDetails.get("Enrollment Status").getText();
            StorageMap.setLocalKeyValue("accountProgramName", accountProgramDetails.get("Account Program Name").getText());
            TestAsserts.assertCondition(true,preApprovedEnrollmentStatus.equalsIgnoreCase(status), "Pre Approval Enrollment Status is: " + preApprovedEnrollmentStatus);
            RunLog.specialInfo("Successfully validated Enrollment Status : " + preApprovedEnrollmentStatus);
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Unable to validate Enrollment Status on BoBo Account Program", "validateEnrollmentStatusOnBoBoAccountProgram()", e.getMessage());
        }
    }

    public void validateApprovalRequestNotification(String requestStatus) {
        String boBoEnrollmentRequestTitle = "Satyam Anand is requesting approval for account program";
        String boBoEnrollmentRequestContent = "Account Program Name: " + StorageMap.getLocalKeyValue("accountProgramName");
        String boBoCancellationRequestTitle = "";
        String boBoCancellationRequestContent = "";
        try{
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
            waitForElement(notificationButton,CLICKABLE,15).click();
            waitForElement(markAsRead,CLICKABLE,30);
            if(requestStatus.equalsIgnoreCase("BoBo Enrollment")) {
                TestAsserts.assertEquals(driver.findElement(notificationTitle).getText().trim(), boBoEnrollmentRequestTitle.trim());
                TestAsserts.assertEquals(driver.findElement(notificationContent).getText().trim(), boBoEnrollmentRequestContent.trim());
                RunLog.info("Successfully Verified BoBo Enrollment - Approval Request Notification");
            }
            if(requestStatus.equalsIgnoreCase("BoBo Cancellation")) {
                TestAsserts.assertEquals(driver.findElement(notificationTitle).getText().trim(), boBoCancellationRequestTitle.trim());
                TestAsserts.assertEquals(driver.findElement(notificationContent).getText().trim(), boBoCancellationRequestContent.trim());
                RunLog.info("Successfully Verified BoBo Enrollment Cancellation - Approval Request Notification");}

            attachScreenShotOfThePageToAllureReport("BoBo Enrollment Approval Request Notification");
            clickElementJS(driver.findElement(notificationContent));
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Unable to validate BoBo Enrollment Approval Request Notification", "validateApprovalRequestNotification()", e.getMessage());
        }
    }

    public void approveBoBoApprovalRequest(String ApprovalComments) {
        try {
            Map<String, WebElement> quickActionButtons = getElementsAsMap(pathForJs + "approvalRequestQuickAction.js", "Approve");
            clickElementJS(quickActionButtons.get("Approve"));
            Map<String, WebElement> approvalPopUp = getElementsAsMap(pathForJs + "approvalFormPopUp.js", "Comments");
            clearAndSendKeys(approvalPopUp.get("Comments"), ApprovalComments.trim());
            Map<String, WebElement> footerButtons = getElementsAsMap(pathForJs + "approvalFormFooterButtons.js", "Cancel");
            footerButtons.get("Approve").click();
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(10));
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Failed to Approve BoBo Approval Request", "approveBoBoApprovalRequest()", e.getMessage());
        }
    }

    public void markALlNotificationsAsRead() {
        try {
            waitForElement(notificationButton,CLICKABLE,15).click();
            waitForElement(markAsRead,CLICKABLE,30).click();
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(4));
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Unable to mark all notification as read", "markALlNotificationsAsRead()", e.getMessage());
        }
    }

    public void validateCancellationDetails(String action, String cancellationReason, String cancellationNotes) {
        try{
            refreshAndWait();
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(3));
            Map<String, WebElement> boBoAccountProgramDetails = getElementsAsMap(pathForCommonJs + "detailsPage.js");
            switch (action) {
                case "Submission":
                    TestAsserts.assertCondition(true, boBoAccountProgramDetails.get("Enrollment Status").getText().equalsIgnoreCase("Enrolled"), "Enrollment Status is not Enrolled");
                    TestAsserts.assertCondition(true, boBoAccountProgramDetails.get("Enrollment Sub-Status").getText().equalsIgnoreCase("Cancellation Requested"), "Enrollment Sub-Status is not Cancellation Requested");
                    RunLog.info("Enrollment Sub-Status is : " + boBoAccountProgramDetails.get("Enrollment Sub-Status").getText());
                    TestAsserts.assertCondition(true, boBoAccountProgramDetails.get("Cancellation Reason").getText().equalsIgnoreCase(cancellationReason), "Cancellation Reason does not match the reason selected");
                    TestAsserts.assertCondition(true, boBoAccountProgramDetails.get("Cancellation Notes").getText().equalsIgnoreCase(cancellationNotes), "Cancellation Notes does not match the note entered");
                    RunLog.info("Successfully validated Cancellation Details after " + action);
                    break;
                case "Approval":
                    TestAsserts.assertCondition(true, boBoAccountProgramDetails.get("Enrollment Status").getText().equalsIgnoreCase("Cancelled"), "Enrollment Status is not Enrolled");
                    TestAsserts.assertCondition(true, boBoAccountProgramDetails.get("Enrollment Sub-Status").getText().equalsIgnoreCase("Cancellation Approved"), "Enrollment Sub-Status is not Cancellation Requested");
                    RunLog.info("Enrollment Sub-Status is : " + boBoAccountProgramDetails.get("Enrollment Sub-Status").getText());
                    TestAsserts.assertCondition(true, boBoAccountProgramDetails.get("Cancellation Reason").getText().equalsIgnoreCase(cancellationReason), "Cancellation Reason does not match the reason selected");
                    TestAsserts.assertCondition(true, boBoAccountProgramDetails.get("Cancellation Notes").getText().equalsIgnoreCase(cancellationNotes), "Cancellation Notes does not match the note entered");
                    RunLog.info("Cancellation Date is: " + boBoAccountProgramDetails.get("Cancellation Date" ).getText());
                    RunLog.info("Successfully validated Cancellation Details after " + action);
                    break;
                default:
                    RunLog.error("Invalid action: " + action);
            }
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Not able to validate Cancellation Details on the BoBoAccountProgram", "validateCancellationDetails()", e.getMessage());
        }
    }

}
