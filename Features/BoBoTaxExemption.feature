Feature: BoBo Tax Exemption in Salesforce

  @BoBoTaxExemption @Salesforce @BoBo @CustomObject @RunAllTest
  Scenario Outline: BoBo Tax Exemption
    Given User launch "<Website>" in "<Browser>"
    And User logs in to Salesforce with the credentials of "<User>"
    When User navigate to an existing "Account Without Tax Exemption"
    And User click on BoBo Tax Exemption button
    Then User fill BoBo Tax Exempt details and submit the form
    And Validate BoBo Tax Exempt Form is submitted successfully
    And Validate BoBo Tax Exempt status on the Account
    And Log out from SFDC
    And Close the browser

    Examples:
      | Browser | Website    | User      |
      | Chrome  | Salesforce | TrialUser |
