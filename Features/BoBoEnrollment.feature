Feature: BoBo Enrollment and Cancellation in Salesforce

  @BoBoEnrollment @Salesforce @BoBo @CustomObject @RunAllTest
  Scenario Outline: BoBo Enrollment Flow - Submit for Approval
    Given User launch "<Website>" in "<Browser>"
    And User logs in to Salesforce with the credentials of "<User>"
    When User navigate to an Account which is eligible for BoBo Enrollment
    Then User click on BoBo Enrollment button
    And User selects the BoBo eligibility requirements on the BoBo Enrollment Form
    And Enter "<BoBo URL>", BoBo Address and select a "<SubProgram>"
    And User selects a contact for the BoBo Account Program
    And Validate BoBo Enrollment is submitted for Approval
    Then Verify Enrollment Status "<PreApprovalStatus>" on Account Program
    And Validate BoBo Customer status on the Account is "false"
    And Validate BoBo Enrollment Approval Request Notification
    And Approve the BoBo Enrollment Request with "<ApprovalComments>"
    And Verify Enrollment Status "<PostApprovalStatus>" on Account Program
    And Validate BoBo Customer status on the Account is "true"
    Then Mark all notification as Read
    And Log out from SFDC
    And Close the browser

    Examples:
      | Browser | Website    | User      | BoBo URL       | SubProgram | PreApprovalStatus | ApprovalComments       | PostApprovalStatus |
      | Chrome  | Salesforce | TrialUser | www.google.com | Bingo      | Out for Approval  | Approved Automatically | Enrolled           |

  @BoBoCancellation @Salesforce @BoBo @CustomObject @RunAllTest
  Scenario Outline: BoBo Cancellation Flow - Submit for Approval
    Given User launch "<Website>" in "<Browser>"
    And User logs in to Salesforce with the credentials of "<User>"
    When User navigate to an existing "BoBo Enrolled Account"
    And Verify Enrollment Status "<PreCancellationStatus>" on Account Program
    Then User click on BoBo Cancellation button
    And User selects the "<CancellationReason>" on the BoBo Cancellation Form
    And Enter "<CancellationNotes>" and submit the enrollment for cancellation
    And Validate BoBo Cancellation is submitted for Approval
    And Verify the Cancellation Details on the Account Program after "Submission"
    And Validate BoBo Enrollment Approval Request Notification
    And Approve the BoBo Enrollment Request with "<ApprovalComments>"
    Then Verify Enrollment Status "<PostCancellationStatus>" on Account Program
    And Verify the Cancellation Details on the Account Program after "Approval"
    And Validate BoBo Customer status on the Account is "false"
    Then Mark all notification as Read
    And Log out from SFDC
    And Close the browser

    Examples:
      | Browser | Website    | User      | PreCancellationStatus | CancellationReason | CancellationNotes | PostCancellationStatus | ApprovalComments       |
      | Chrome  | Salesforce | TrialUser | Enrolled              | BoBo Burnout       | Cancellation Test | Cancelled              | Approved Automatically |

  @BoBoEnrollmentWithoutContact @Salesforce @BoBo @CustomObject @RunAllTest
  Scenario Outline: BoBo Enrollment - Account without Contact
    Given User launch "<Website>" in "<Browser>"
    And User logs in to Salesforce with the credentials of "<User>"
    When User navigate to an existing "Account Without Contact"
    And User click on BoBo Enrollment button
    Then Validate user should not be able to proceed with BoBo Enrollment
    And Log out from SFDC
    And Close the browser

    Examples:
      | Browser | Website    | User      |
      | Chrome  | Salesforce | TrialUser |

  @BoBoEnrollmentOnAlreadyEnrolledAccount @Salesforce @BoBo @CustomObject @RunAllTest
  Scenario Outline: BoBo Enrollment - Account already enrolled in BoBo
    Given User launch "<Website>" in "<Browser>"
    And User logs in to Salesforce with the credentials of "<User>"
    When User navigate to an existing "BoBo Enrolled Account"
    And User click on BoBo Enrollment button
    Then User should see an error message indicating the account is already enrolled
    And Log out from SFDC
    And Close the browser

    Examples:
      | Browser | Website    | User      |
      | Chrome  | Salesforce | TrialUser |
