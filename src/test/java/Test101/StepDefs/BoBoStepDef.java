package Test101.StepDefs;

import Test101.Factory.PageFactory.MyPageFactory;
import Test101.WebServices.Salesforce.Salesforce;
import Test101.utils.StorageMap;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;

public class BoBoStepDef extends MyPageFactory  {

    @When("User navigate to an Account which is eligible for BoBo Enrollment")
    @Step("User navigate to an Account which is eligible for BoBo Enrollment")
    public void createNewAccountUsingAPI() {
        String newAccountId = new Salesforce().account().create();
        new Salesforce().contact().create(newAccountId);
        StorageMap.setLocalKeyValue("accountId", newAccountId);
        sfdcAccountDetailsPage().userNavigateToAccount(newAccountId);
        sfdcAccountDetailsPage().navigateToTab("Details");
    }

    @Then("User click on BoBo Enrollment button")
    @Step("User click on BoBo Enrollment button")
    public void userClickOnBoBoEnrollmentButton() {
        boBoEnrollmentPage().boBoEnrollmentComponent().selectBoBoEnrollmentButton();
    }

    @And("User selects the BoBo eligibility requirements on the BoBo Enrollment Form")
    @Step("User selects the BoBo eligibility requirements on the BoBo Enrollment Form")
    public void userSelectsTheBoBoEligibilityRequirementsOnTheBoBoEnrollmentForm() {
        boBoEnrollmentPage().boBoEnrollmentComponent().userSelectBoBoEnrollmentRequirements();
    }

    @And("Enter {string}, BoBo Address and select a {string}")
    @Step("Enter BoBoURL and select a SubProgram")
    public void enterURLandSelectASubProgram(String boBoURL, String SubProgram) {
        boBoEnrollmentPage().boBoEnrollmentComponent().userEnterBoBoDetails(boBoURL,SubProgram);
    }

    @And("User selects a contact for the BoBo Account Program")
    @Step("User selects a contact for the BoBo Account Program")
    public void selectAContactForBoBoAccountProgram(){
        boBoEnrollmentPage().boBoEnrollmentComponent().selectContact(1);
    }

    @And("Validate BoBo Enrollment is submitted for Approval")
    @Step("Validate BoBo Enrollment is submitted for Approval")
    public void validateBoBoEnrollmentIsSubmittedForApproval() {
        boBoEnrollmentPage().boBoEnrollmentComponent().validateFlowCompletedMessage();
    }

    @Then("Verify Enrollment Status {string} on Account Program")
    @Step("Verify Enrollment Status on Account Program")
    public void verifyEnrollmentStatusOnAccountProgram(String status) {
        boBoAccountProgramPage().boBoAccountProgramComponent().navigateToBoBoAccountProgram(StorageMap.getLocalKeyValue("accountId"));
        sfdcAccountDetailsPage().navigateToTab("Details");
        boBoAccountProgramPage().boBoAccountProgramComponent().validateEnrollmentStatusOnBoBoAccountProgram(status);
    }

    @And("Validate BoBo Enrollment Approval Request Notification")
    @Step("Validate BoBo Enrollment Approval Request Notification")
    public void validateBoBoEnrollmentApprovalRequestNotification() {
        boBoAccountProgramPage().boBoAccountProgramComponent().validateApprovalRequestNotification("BoBo Enrollment");
    }

    @And("Approve the BoBo Enrollment Request with {string}")
    @Step("Approve the BoBo Enrollment Request with Approval Comments")
    public void approveTheBoBoEnrollmentRequestWith(String approvalComments) {
        boBoAccountProgramPage().boBoAccountProgramComponent().approveBoBoApprovalRequest(approvalComments);
    }

    @And("Validate BoBo Customer status on the Account is {string}")
    @Step("Validate BoBo Customer status on the Account")
    public void validateBoBoCustomerStatusOnTheAccountIs(String status) {
        sfdcAccountDetailsPage().accountDetails().validateBoBoCustomerStatus(StorageMap.getLocalKeyValue("accountId"), Boolean.valueOf(status));
    }

    @Then("User click on BoBo Cancellation button")
    @Step("User click on BoBo Cancellation button")
    public void userClickOnBoBoCancellationButton() {
        boBoCancellationPage().boBoCancellationComponent().selectCancellationButton();
    }

    @And("User selects the {string} on the BoBo Cancellation Form")
    @Step("User selects the cancellation reason on the BoBo Cancellation Form")
    public void userSelectsTheOnTheBoBoCancellationForm(String cancellationReason) {
        boBoCancellationPage().boBoCancellationComponent().selectCancellationReason(cancellationReason);
        StorageMap.setLocalKeyValue("cancellationReason", cancellationReason);
    }

    @And("Enter {string} and submit the enrollment for cancellation")
    @Step("Enter Cancellation Notes and submit the enrollment for cancellation")
    public void enterAndSubmitTheEnrollmentForCancellation(String cancellationNotes) {
        boBoCancellationPage().boBoCancellationComponent().enterCancellationNotes(cancellationNotes);
        StorageMap.setLocalKeyValue("cancellationNotes", cancellationNotes);
    }

    @And("Validate BoBo Cancellation is submitted for Approval")
    @Step("Validate BoBo Cancellation is submitted for Approval")
    public void validateBoBoCancellationIsSubmittedForApproval() {
        boBoCancellationPage().boBoCancellationComponent().validateFlowCompletedMessage();
    }

    @Then("Mark all notification as Read")
    @Step("Marking all notifications as Read")
    public void markAllNotificationAsRead() {
        boBoAccountProgramPage().boBoAccountProgramComponent().markALlNotificationsAsRead();
    }

    @And("Verify the Cancellation Details on the Account Program after {string}")
    @Step("Verify the Cancellation Details on the Account Program after action")
    public void verifyTheCancellationDetailsOnTheAccountProgramAfter(String action) {
        boBoAccountProgramPage().boBoAccountProgramComponent().validateCancellationDetails(action, StorageMap.getLocalKeyValue("cancellationReason"), StorageMap.getLocalKeyValue("cancellationNotes"));
    }

    @Then("Validate user should not be able to proceed with BoBo Enrollment")
    @Step("Validate user should not be able to proceed with BoBo Enrollment")
    public void boBoEnrollmentOnAccountWithoutContact() {
        boBoEnrollmentPage().boBoEnrollmentComponent().boBoEnrollmentOnAccountWithoutContact();
    }

    @Then("User should see an error message indicating the account is already enrolled")
    @Step("User should see an error message indicating the account is already enrolled")
    public void validateErrorMsgAccountAlreadyEnrolled() {
        boBoEnrollmentPage().boBoEnrollmentComponent().boBoEnrollmentOnAccountAlreadyEnrolled();
    }
}
