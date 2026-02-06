package Test101.Components.SFDC;

import Test101.utils.StorageMap;
import base.RunLog;
import base.TestAsserts;
import Test101.Components.Common;
import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.Duration;
import java.util.*;

import java.util.concurrent.TimeUnit;

public class AccountDetails extends Common {

    String pathForJs="jsFiles/";
    public void navigateToAccount(String accountName) {
        try {
            String baseUrl = environment.getValue("sfdc_base_url");
            String accountId = environment.getValue(accountName.concat("_ID"));
            RunLog.info("Account URL: " + baseUrl + accountId);
            driver.get(baseUrl + accountId);
            Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
            attachScreenShotOfThePageToAllureReport("Navigated to Account");
        } catch (AssertionError | Exception e) {
            logErrorAndTakeScreenshot("Failed at Getting Account ID and navigation to account page ", "navigateToAccount()", e.getMessage());
        }
    }

    public void userNavigateToAccount(String accountId) {
        try {
            String baseUrl = environment.getValue("sfdc_base_url");
            RunLog.info("Account URL: " + baseUrl + accountId);
            driver.get(baseUrl + accountId);
            Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
        } catch (AssertionError | Exception e) {
            logErrorAndTakeScreenshot("Failed at navigation to account page ", "userNavigateToAccount()" , e.getMessage());
        }
    }

    public void navigateToTab(String tabName) {
        try {
            Map<String, WebElement> tabMap = getElementsAsMap(pathForJs + "relatedTabs.js", "Details");
            WebElement tab = tabMap.get(tabName);
            actionClick(tab);
            Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
            RunLog.info("Navigated to " + tabName + " tab");
            attachScreenShotOfThePageToAllureReport(tabName + " Page of the Salesforce Object");
        } catch (AssertionError | Exception e) {
            logErrorAndTakeScreenshot("Not able to get the " + tabName + " tab from account details page ", "navigateToTab()", e.getMessage());
        }
    }

    public void validateAccountData() {
        try{
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
            Map<String, WebElement> quickLinks = getElementsAsMap(pathForJs+ "detailsPage.js", "Account Name");
            String accountName = "Account Name: " + quickLinks.get("Account Name").getText();
            String accountNumber = "Account Number: " + quickLinks.get("Account Number").getText();
            String type = "Account Type: " + quickLinks.get("Type").getText();
            String ownership = "Account Ownership: " + quickLinks.get("Ownership").getText();
            String industry = "Account Industry: " + quickLinks.get("Industry").getText();
            String phone = "Account Phone: " + quickLinks.get("Phone").getText();
            String billingAddress = "Billing Address: " + quickLinks.get("Billing Address").getText();
            Map<String, WebElement> detailsPageLookups = getElementsAsMap(pathForJs+ "detailsPageLookUps.js");
            String accountOwner = "Account Owner: " + detailsPageLookups.get("Account Owner");
            String createdBy = "Created By: " + detailsPageLookups.get("Created By");
            String[] details = {accountOwner, accountName, accountNumber, type, ownership, industry, phone, createdBy, billingAddress};
            RunLog.specialInfo("Account Details: " );
            for (String detail : details) {
                RunLog.info("• " + detail);
            }
            TestAsserts.assertTextContainsIgnoreCase(accountOwner, "Satyam Anand");
            RunLog.specialInfo("Account Data validation successful");
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
        } catch (AssertionError | Exception e) {
            logErrorAndTakeScreenshot("Not able to get the validate the Account Data ", "validateAccountData()" ,e.getMessage());
        }
    }

    public String getTextFromDetailsPageFor(String labelName) {
        try{
            Map<String, WebElement> quickLinks = getElementsAsMap(pathForJs+ "detailsPage.js","Account Name");
            String labelValue = quickLinks.get(labelName).getText();
            RunLog.info(labelName + " : " + labelValue);
            return labelValue;
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Not able to get the text from " + labelName + " field. ", "getTextFromDetailsPageFor()", e.getMessage());
            return null;
        }
    }

    public void validateBoBoCustomerStatus(String accountId, Boolean status) {
        try{
            userNavigateToAccount(accountId);
            navigateToTab("Details");
            Map<String, WebElement> accountDetails = getElementsAsMap(pathForJs+ "detailsPage.js","BoBo Customer");
            if (status) {
                // Assert that the actual status is true (selected)
                TestAsserts.assertCondition(true, accountDetails.get("BoBo Customer").isSelected(), "BoBo Customer Status is not " + status);
                RunLog.info("Validated BoBo Customer Status is Checked");
            } else {
                // Assert that the actual status is false (not selected)
                TestAsserts.assertCondition(false, accountDetails.get("BoBo Customer").isSelected(), "BoBo Customer Status is not: " + status);
                RunLog.info("Validated BoBo Customer Status Not Checked");
            }
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Not able Validate BoBo Customer Status on Account Details Page", "validateBoBoCustomerStatus()", e.getMessage());
        }
    }

    public void validateBoBoTaxExemptStatus(String accountId) {
        try{
            userNavigateToAccount(accountId);
            navigateToTab("Details");
            Map<String, WebElement> accountDetails = getElementsAsMap(pathForJs+ "detailsPage.js","BoBo Tax Exempt");
            TestAsserts.assertCondition(true, accountDetails.get("BoBo Tax Exempt").isSelected(), "BoBo Tax Exempt Status is not true");
            RunLog.info("Validated BoBo Tax Exempt Status is Checked");
            TestAsserts.assertCondition(true, StorageMap.getLocalKeyValue("TaxCertificateNumber").contains(accountDetails.get("BoBo Tax Exempt #").getText()), "BoBo Tax Exempt Status is not true");
            RunLog.info("Validated BoBo Tax Exempt Status is Checked");
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Not able Validate BoBo Customer Status on Account Details Page", "validateBoBoCustomerStatus()", e.getMessage());
        }
    }

}
