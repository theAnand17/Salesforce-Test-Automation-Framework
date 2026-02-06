package Test101.StepDefs;

import Test101.Factory.PageFactory.MyPageFactory;
import Test101.utils.StorageMap;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.qameta.allure.Step;

public class TaxExemptionStepDef extends MyPageFactory {

    @Then("User click on BoBo Tax Exemption button")
    @Step("User click on BoBo Tax Exemption button")
    public void userClickOnBoBoTaxExemptionButton() {
        boBoTaxExemptPage().boBoTaxExemptComponent().selectBoBoTaxExemptButton();
    }

    @Then("User fill BoBo Tax Exempt details and submit the form")
    @Step("User fill BoBo Tax Exempt details and submit the form")
    public void userFillBoBoTaxExemptDetailsAndSubmitTheForm() {
        boBoTaxExemptPage().boBoTaxExemptComponent().userFillTaxExemptForm();
    }

    @And("Validate BoBo Tax Exempt Form is submitted successfully")
    @Step("Validate BoBo Tax Exempt Form is submitted successfully")
    public void validateBoBoTaxExemptFormIsSubmittedSuccessfully() {
        boBoTaxExemptPage().boBoTaxExemptComponent().validateTaxExemptFormIsSubmitted();
    }

    @And("Validate BoBo Tax Exempt status on the Account")
    @Step("Validate BoBo Tax Exempt status on the Account")
    public void validateBoBoTaxExemptStatus() {
        sfdcAccountDetailsPage().accountDetails().validateBoBoTaxExemptStatus(StorageMap.getLocalKeyValue("accountId"));
    }

}
