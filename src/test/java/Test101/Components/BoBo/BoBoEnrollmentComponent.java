package Test101.Components.BoBo;

import Test101.Components.Common;
import base.RunLog;
import base.TestAsserts;
import com.github.javafaker.Faker;
import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.Map;

public class BoBoEnrollmentComponent extends Common {

    String pathForJs="jsFiles/BoBo/";
    String pathForCommonJs="jsFiles/";
    private static final Faker faker = new Faker();

    public void selectBoBoEnrollmentButton(){
        try{
            Map<String, WebElement> quickActionButtons = getElementsAsMap(pathForCommonJs + "quickActionButtons.js","BoBo Enrollment");
            clickElementJS(quickActionButtons.get("BoBo Enrollment"));
            RunLog.info("The user has successfully clicked on the 'BoBo Enrollment' button.");
        } catch(Exception e){
            logErrorAndTakeScreenshot("The user is unable to select the 'BoBo Enrollment' button from Quick Actions." , "selectBoBoEnrollmentButton()", e.getMessage());
        }
    }

    public void clickNextButtonOnFooter() {
        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
        Map<String, WebElement> footerButtons = getElementsAsMap(pathForCommonJs + "formFooter.js","Next");
        clickElementJS(footerButtons.get("Next"));
        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
    }

    public void clickFinishButtonOnFooter() {
        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
        Map<String, WebElement> footerButtons = getElementsAsMap(pathForCommonJs + "formFooter.js","Finish");
        clickElementJS(footerButtons.get("Finish"));
        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
        RunLog.info("Successfully clicked on the 'Finish' button and closed the Enrollment Form.");
    }

    public void userSelectBoBoEnrollmentRequirements(){
        try {
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(3));
            Map<String, WebElement> quickLinks = getElementsAsMap(pathForJs + "boBoRequirements.js");
            clickElementJS(quickLinks.get("1. Maintain Current Account Status with FunToys, GiggleGoods, ToyTopia, and WhimsicalWonders."));
            clickElementJS(quickLinks.get("2. Members must adhere to the BoBo code of joy, ensuring playful and fun business practices."));
            clickElementJS(quickLinks.get("3. Members must offer and honor the Whimsical Warranty program, providing joyous experiences to BoBo enthusiasts."));
            clickElementJS(quickLinks.get("4. Members must identify their store or play area as a BoBo facility, ensuring a whimsical and inviting atmosphere."));
            RunLog.info("Successfully selected requirements for BoBo Enrollment Eligibility");
            attachScreenShotOfThePageToAllureReport("BoBo Requirements");
            clickNextButtonOnFooter();
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Not able to select requirements for BoBo Enrollment Eligibility", "userSelectBoBoEnrollmentRequirements()", e.getMessage());
        }
    }

    public void userEnterBoBoDetails(String boBoURL, String subProgram){
        try {
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(3));
            String boBoName = faker.funnyName().name();
            String boboPhone = "9876543210";

            Map<String, WebElement> boBoDetails = getElementsAsMap(pathForJs + "boBoDetails.js","*BoBo Name");
            clearAndSendKeys(boBoDetails.get("*BoBo Name"), boBoName);
            clearAndSendKeys(boBoDetails.get("BoBo Phone #"), boboPhone);
            clearAndSendKeys(boBoDetails.get("BoBo Web URL"), boBoURL);

            if (subProgram.equalsIgnoreCase("Whimsical Warranty")) {
                Map<String, WebElement> additionalPrograms = getElementsAsMap(pathForJs + "boBoAdditionalPrograms.js");
                clickElementJS(additionalPrograms.get("Whimsical Warranty"));
                RunLog.info("Additional Program Selected: Whimsical Warranty");
            } else if (subProgram.equalsIgnoreCase("Bingo")){
                Map<String, WebElement> additionalPrograms = getElementsAsMap(pathForJs + "boBoAdditionalPrograms.js");
                clickElementJS(additionalPrograms.get("Bingo"));
                RunLog.info("Additional Program Selected: Bingo");
            }

            Map<String, WebElement> boBoAddress = getElementsAsMap(pathForJs + "boBoAddress.js","Street");
            clearAndSendKeys(boBoAddress.get("Street"), faker.address().streetName());
            clearAndSendKeys(boBoAddress.get("City"), faker.address().city());
            clearAndSendKeys(boBoAddress.get("Zip/Postal Code"), String.valueOf(faker.number().randomNumber(6, true)));
            clearAndSendKeys(boBoAddress.get("State/Province"), faker.address().state());
            clearAndSendKeys(boBoAddress.get("Country"), faker.address().country());
            RunLog.info("Successfully entered BoBo Details for BoBo Enrollment");

            attachScreenShotOfThePageToAllureReport("BoBo Details");
            clickNextButtonOnFooter();
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Not able to enter BoBo Details", "userEnterBoBoDetails()", e.getMessage());
        }
    }

    public void selectContact(int contactIndex) {
        try {
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
            selectOptionFromDropdown(pathForJs + "boBoPicklists.js", "Available Contacts for Enrollment",contactIndex);
            RunLog.info("Successfully selected Contact for BoBo Enrollment");
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
            attachScreenShotOfThePageToAllureReport("BoBo Contact");
            clickNextButtonOnFooter();
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Not able to select BoBo Contact", "selectContact()", e.getMessage());
        }
    }

    public void validateFlowCompletedMessage(){
        try {
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
            Map<String, WebElement> boBoMsg = getElementsAsMap(pathForJs + "boBoFlowCompleteMsg.js");
            WebElement BoBoSuccessMsg = boBoMsg.get("Congratulations! 🎉 You've successfully completed the whimsical journey through the BoBo Enrollment Flow, where joy and giggles abound! Now, brace yourself for the next step: enrollment approval by the one and only Grand Master of Giggles, the Sultan of Silly, the Duke of Doodles, and the Supreme Supervisor of Shenanigans! Get ready to be enrolled in the magical world of BoBo with a sprinkle of laughter and a dash of delight! Let the chuckles commence!");
            TestAsserts.assertCondition(true, BoBoSuccessMsg.isDisplayed(), "BoBo Flow Complete Message is Not Displayed");
            RunLog.specialInfo("Enrollment process for BoBo has been successfully completed. Upon approval, the customer will be enrolled.");
            attachScreenShotOfThePageToAllureReport("BoBo Flow Complete");
            clickFinishButtonOnFooter();
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Not able to validate BoBo Enrollment Flow Completed Message", "validateFlowCompletedMessage()", e.getMessage());
        }
    }

    public void boBoEnrollmentOnAccountWithoutContact(){
        try {
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
            Map<String, WebElement> boBoMsg = getElementsAsMap(pathForJs + "boBoFlowCompleteMsg.js");
            WebElement BoBoNoContactMsg = boBoMsg.get("Currently there are no Contacts available for Enrollment. Please create at least one contact prior to BoBo enrollment. This will allow them to access the BoBo Member site once enrolled.");
            TestAsserts.assertCondition(true, BoBoNoContactMsg.isDisplayed(), "No Contact on Account Message is Not Displayed");
            RunLog.specialInfo("Validation Successful: BoBo Enrollment halted as anticipated when no contacts are found on the account");
            attachScreenShotOfThePageToAllureReport("BoBo Enrollment No Contact Error");
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Not able to validate BoBo Enrollment Flow Error when Contact is not present on the Account", "boBoEnrollmentOnAccountWithoutContact()", e.getMessage());
        }
    }

    public void boBoEnrollmentOnAccountAlreadyEnrolled(){
        try {
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
            Map<String, WebElement> boBoMsg = getElementsAsMap(pathForJs + "boBoFlowCompleteMsg.js");
            WebElement BoBoNoContactMsg = boBoMsg.get("BoBo enrollment is not possible for this account as it currently holds an active enrollment or is undergoing the enrollment process.");
            TestAsserts.assertCondition(true, BoBoNoContactMsg.isDisplayed(), "BoBo Enrollment Already Exist Message is Not Displayed");
            RunLog.specialInfo("Validation Successful: BoBo Enrollment halted as anticipated due to account already being enrolled");
            attachScreenShotOfThePageToAllureReport("BoBo Enrollment Account Already Enrolled Error");
            clickFinishButtonOnFooter();
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Not able to validate BoBo Enrollment Flow Error when Account is already enrolled", "boBoEnrollmentOnAccountAlreadyEnrolled()", e.getMessage());
        }
    }

}
