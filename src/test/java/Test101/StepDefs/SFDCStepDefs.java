package Test101.StepDefs;

import Test101.Components.SFDC.AccountDataFactory;
import Test101.Components.SFDC.DataGenerator;
import Test101.Factory.PageFactory.MyPageFactory;
import Test101.WebServices.Salesforce.Salesforce;
import Test101.utils.StorageMap;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SFDCStepDefs extends MyPageFactory {

    public static String accountId;
    public static String accountName;

    protected Map<String, String> accountData;
    protected Map<String, String> contactData;
    private Map<String, String> mergedAccountData;

    @When("User login to SFDC as {string}")
    @Step("Login to SFDC as user")
    public void sfdcLogin(String user) {
        sfdcLoginPage().loginToSFDCAsUser(user);
    }

    @When("User logs in to Salesforce with the credentials of {string}")
    @Step("User logs in to Salesforce with the credentials of Provided User")
    public void sfdcAPILogin(String user) {
        sfdcLoginPage().loginToSalesforce(user);
    }

    @Then("Navigate to {string} account")
    @Step("Navigate to a given Account")
    public void navigateToAccount(String accountName) {
        sfdcAccountDetailsPage().navigateToAccount(accountName);
    }

    @And("Validate the data on {string} tab")
    @Step("Validate the data on a given tab")
    public void validateData(String tabName) {
        sfdcAccountDetailsPage().navigateToTab(tabName);
        sfdcAccountDetailsPage().validateAccountDetails();
        sfdcAccountDetailsPage().accountDetail().getTextFromDetailsPageFor("Description");
    }

    @And("Log out from SFDC")
    @Step("Log out from SFDC")
    public void logoutFromCRM() {
        sfdcLoginPage().logoutFromSFDC();
    }

    @Then("Navigate to new account page")
    @Step("Navigate to new account page")
    public void navigateToNewAccountPage() {
        sfdcAccountsPage().navigateToNewAccountPage();
    }

    @And("User create a new account with below details")
    @Step("User create a new account with below details")
    public void createNewAccount(DataTable table){
        List<Map<String, String>> dataRow = table.asMaps(String.class, String.class);
        accountData = DataGenerator.generateAccountData();
        // Merge data from DataTable with generated data
        accountData.putAll(dataRow.get(0));
        mergedAccountData = new HashMap<>(accountData);
        sfdcAccountsPage().createNewAccountRecord(mergedAccountData);
    }

    @And("Validate the new account record data on {string} tab")
    @Step("Validate the new account record data on given tab")
    public void validateNewAccountRecord(String tabName) {
        sfdcAccountDetailsPage().navigateToTab(tabName);
        sfdcAccountsPage().validateNewAccountRecord(mergedAccountData);
    }

    @Then("User navigate to an existing {string}")
    @Step("User navigate to an existing account")
    public void userNavigateToAnExisting(String accountRequirement) {
        accountId = new AccountDataFactory().getIdFor(accountRequirement.trim());
        StorageMap.setLocalKeyValue("accountId", accountId);
        sfdcAccountDetailsPage().userNavigateToAccount(accountId);
        sfdcAccountDetailsPage().navigateToTab("Details");
        accountName = sfdcAccountDetailsPage().accountDetail().getTextFromDetailsPageFor("Account Name");
    }

    @When("User navigate to new contact page")
    @Step("User navigate to new contact page")
    public void userNavigateToNewContactPage() {
        sfdcContactPage().createNewContact().navigateToNewContactPage();
    }

    @And("Create new contact on the account")
    @Step("Create new contact on the account")
    public void createNewContactOnAccount() {
        contactData = DataGenerator.generateContactData();
        sfdcContactPage().createNewContact().createContactRecord(contactData, accountName);
    }

    @And("Validate the new contact record data on {string} tab")
    @Step("Validate the new contact record data on given tab")
    public void validateNewContactRecord(String tabName) {
        sfdcAccountDetailsPage().navigateToTab(tabName);
        sfdcContactPage().createNewContact().validateNewContactData(accountName);
    }

    @And("User creates a new account")
    @Step("User creates a new account")
    public void createNewAccountUsingAPI() {
        String newAccountId = new Salesforce().account().create();
        sfdcAccountDetailsPage().userNavigateToAccount(newAccountId);
    }
}
