Feature: Framework Examples - Google and Flipkart Search
  # These are example test scenarios to help you learn the framework
  # They test public websites (Google, Flipkart) that don't require credentials
  # Use these to verify your setup before running Salesforce tests

  @FlipkartSearchResult @Flipkart @Examples
  Scenario Outline: Search for products on Flipkart and select from suggestions
    Given User launch "<Website>" in "<Browser>"
    When Search for "<SearchText>"
    Then Select "<Suggestion>" from the suggestion list
    And Validate search result is displayed and save screenshot with "<ScreenshotName>"
    And Close the browser

    Examples:
      | Browser | Website  | SearchText        | Suggestion | ScreenshotName                |
      | Chrome  | Flipkart | Samsung S24 Ultra |          1 | SamsungSearchResultOnFlipkart |
      | Edge    | Flipkart | Apple iPhone 15   |          3 | AppleSearchResultOnFlipkart   |
      | Chrome  | Flipkart | Google Pixel 8    |          2 | PixelSearchResultOnFlipkart   |

  @FlipkartNavBar @Flipkart @Examples
  Scenario Outline: Extract navigation links from Flipkart homepage
    Given User launch "<Browser>" and navigate to "<Website>"
    Then User gets all the links form the navigation bar
    And Close the browser

    Examples:
      | Browser | Website               |
      | Chrome  | www.flipkart.com/plus |
