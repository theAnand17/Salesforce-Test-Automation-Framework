package Test101.Components.BoBoTaxExempt;

import Test101.Components.Common;
import Test101.utils.StorageMap;
import base.RunLog;
import base.TestAsserts;
import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class BoBoTaxExemptComponent extends Common {

    String pathForJs="jsFiles/BoBoTaxExempt/";
    String pathForCommonJs="jsFiles/";
    String pathForBoBoJs = "jsFiles/BoBo/";

    public void selectBoBoTaxExemptButton(){
        try{
            Map<String, WebElement> quickActionButtons = getElementsAsMap(pathForCommonJs + "quickActionButtons.js","BoBo Enrollment");
            clickElementJS(quickActionButtons.get("BoBo Tax Exemption"));
            RunLog.info("The user has successfully clicked on the 'BoBo Tax Exemption' button.");
        } catch(Exception e){
            logErrorAndTakeScreenshot("The user is unable to select the 'BoBo Tax Exemption' button from Quick Actions." , "selectBoBoTaxExemptButton()", e.getMessage());
        }
    }

    public void clickSubmitButtonOnFooter() {
        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
        Map<String, WebElement> footerButtons = getElementsAsMap(pathForCommonJs + "formFooter.js","Submit");
        clickElementJS(footerButtons.get("Submit"));
        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
    }

    public void clickFinishButtonOnFooter() {
        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
        Map<String, WebElement> footerButtons = getElementsAsMap(pathForCommonJs + "formFooter.js","Finish");
        clickElementJS(footerButtons.get("Finish"));
        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
        RunLog.info("Successfully clicked on the 'Finish' button and closed the Tax Exempt Form.");
    }

    public String generateCertificateNumber() {
        Set<String> generatedCertificates = new HashSet<>();
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        while (generatedCertificates.size() < 100000) {
            int randomNumber = random.nextInt(max - min + 1) + min;
            String certificateNumber = "BOBO" + String.format("%06d", randomNumber); // Add "BOBO" prefix
            generatedCertificates.add(certificateNumber);
        }
        return generatedCertificates.iterator().next();
    }

    public void userFillTaxExemptForm() {
        try {
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(3));
            String taxCertificateNumber = generateCertificateNumber();
            StorageMap.setLocalKeyValue("TaxCertificateNumber", taxCertificateNumber);
            Map<String, WebElement> boBoTaxExemptFormFields = getElementsAsMap(pathForJs + "boBoTaxExemptForm.js","*BoBo Tax Exempt #");
            clearAndSendKeys(boBoTaxExemptFormFields.get("*BoBo Tax Exempt #"), taxCertificateNumber);
            clearAndSendKeys(boBoTaxExemptFormFields.get("Case Comments"), "Please Exempt this Customer from BoBo Tax");

            String taxCertificateFilePath = System.getProperty("user.dir") + "//src//test//resources//Files//BoBo Tax Exemption Certificate.jpg";
            RunLog.info("File to be uploaded : " + taxCertificateFilePath);
            WebElement uploadButton = boBoTaxExemptFormFields.get("Upload FilesOr drop files");
            uploadButton.sendKeys(taxCertificateFilePath);
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));

            Map<String, WebElement> buttons = getElementsAsMap(pathForJs + "uploadFileButtons.js", "Done");
            WebElement doneButton = buttons.get("Done");
            wait(30).until(ExpectedConditions.elementToBeClickable(doneButton));
            attachScreenShotOfThePageToAllureReport("Tax Certificate Uploaded");
            actionClick(doneButton);
            RunLog.info("BoBo Tax Exempt Certificate File Uploaded Successfully");
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(3));
            attachScreenShotOfThePageToAllureReport("BoBo Tax Exempt Form");
            clickSubmitButtonOnFooter();
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Not able to enter BoBo Tax Exempt Details", "userFillTaxExemptForm()", e.getMessage());
        }
    }

    public void validateTaxExemptFormIsSubmitted(){
        try {
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
            Map<String, WebElement> boBoMsg = getElementsAsMap(pathForBoBoJs + "boBoFlowCompleteMsg.js");
            WebElement TaxExemptSubmittedMsg = boBoMsg.get("Hooray! You've officially outsmarted the taxman with your BoBo Tax exemption certificate upload!");
            TestAsserts.assertCondition(true, TaxExemptSubmittedMsg.isDisplayed(), "BoBo Tax Exempt Form Submitted Message Not Displayed");
            RunLog.specialInfo("BoBo Tax Exempt Form has been submitted successfully");
            attachScreenShotOfThePageToAllureReport("Tax Exempt Form Submitted");
            clickFinishButtonOnFooter();
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Not able to validate BoBo Tax Exempt Form Submitted Message", "validateTaxExemptFormIsSubmitted()", e.getMessage());
        }
    }

}
