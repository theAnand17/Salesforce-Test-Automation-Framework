Feature: Salesforce data creation

  @SFDCAccountCreation @Salesforce @SalesforceTest @RunAllTest
  Scenario Outline: Create a new account in Salesforce
    Given User launch "<Website>" in "<Browser>"
    When User logs in to Salesforce with the credentials of "<User>"
    Then Navigate to new account page
    And User create a new account with below details
    | PhoneNumber | Industry    | Ownership | Type     |
    | 9876512340  | Engineering | Private   | Prospect |
    And Validate the new account record data on "Details" tab
    And Log out from SFDC
    And Close the browser
    Examples:
      | Browser | Website    | User      |
      | Edge    | Salesforce | TrialUser |

  @SFDCContactCreation @Salesforce @SalesforceTest @RunAllTest
  Scenario Outline: Create a contact on an existing account in Salesforce
    Given User launch "<Website>" in "<Browser>"
    When User logs in to Salesforce with the credentials of "<User>"
    Then User navigate to an existing "Account Without Contact"
    When User navigate to new contact page
    And Create new contact on the account
    And Validate the new contact record data on "Details" tab
    And Log out from SFDC
    And Close the browser
    Examples:
      | Browser | Website    | User      |
      | Chrome  | Salesforce | TrialUser |

  @SFDCAccountCreationUsingAPI @Salesforce @SalesforceTest @RunAllTest
  Scenario Outline: Create a new account in Salesforce using API
    Given User launch "<Website>" in "<Browser>"
    When User logs in to Salesforce with the credentials of "<User>"
    Then User creates a new account
    And Validate the data on "Details" tab
    And Log out from SFDC
    And Close the browser
    Examples:
      | Browser | Website    | User      |
      | Chrome  | Salesforce | TrialUser |