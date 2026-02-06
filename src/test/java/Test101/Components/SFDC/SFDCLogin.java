package Test101.Components.SFDC;

import Test101.Components.SFDC.Login.SalesforceConnection;
import Test101.utils.SalesforceClient;
import base.Environment;
import base.PasswordDecrypt;
import base.RunLog;
import Test101.Components.Common;
import Test101.Components.Element;
import base.TestAsserts;
import com.google.common.util.concurrent.Uninterruptibles;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SFDCLogin extends Common {

    String locatorsPageName = "SFDCLocators";
    private SalesforceConnection con;
    private String sessionId;
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private final Lock writeLock = reentrantReadWriteLock.writeLock();
    Environment environment = new Environment();


    @FindBy
    private By inputUsername;
    private By inputPassword;
    private By btnLogin;
    private By txtService;

    public SFDCLogin() {
        try {
            assignLocatorPage();
        } catch (Exception e) {
            RunLog.error(e.getMessage());
        }
    }

    public void assignLocatorPage() {
        inputUsername = Element.returnBrowserElementFromRepo(locatorsPageName, "SFDCLoginComponent", "inputUsername");
        inputPassword = Element.returnBrowserElementFromRepo(locatorsPageName, "SFDCLoginComponent", "inputPassword");
        btnLogin = Element.returnBrowserElementFromRepo(locatorsPageName, "SFDCLoginComponent", "btnLogin");
        txtService = Element.returnBrowserElementFromRepo(locatorsPageName, "SFDCLoginComponent", "txtService");
    }

    public void loginToSalesforceFromLoginPage(String user) {
        try {
            String username = environment.getValue(user.concat("_Username"));
            String encryptedPassword = environment.getValue(user.concat("_Password"));
            String password = PasswordDecrypt.decrypt_Password(encryptedPassword);
            // Find the username input element and enter the username
            waitForElementToBeVisible(inputUsername);
            WebElement usernameInput = driver.findElement(inputUsername);
            usernameInput.sendKeys(username);

            // Find the password input element and enter the password
            WebElement passwordInput = driver.findElement(inputPassword);
            passwordInput.sendKeys(password);

            // Find the login button and click it to submit the form
            WebElement loginButton = driver.findElement(btnLogin);
            loginButton.click();

            // Wait for the page to load and check if the specified element is visible
            waitForElementToBeVisible(txtService);
            RunLog.info("Login successful!");
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
            attachScreenShotOfThePageToAllureReport("Login successful!");
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Failed to login to Salesforce.", "loginToSFDC() ", e.getMessage());
        }
    }

    public void logoutFromSFDC() {
        new Logout().get();
        RunLog.info("Logged out from SFDC ");
        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
        attachScreenShotOfThePageToAllureReport("Salesforce Login Page after Logout");
    }

    //Salesforce Login using SOAP API
    public void loginToSalesforce(String user){
        try{
            RunLog.info("Initiating login process to Salesforce using SOAP API...");
            String application = "CRM";
            String usernameKey = user.concat("_Username");
            String securePasswordKey = user.concat("_SecurePassword");
            login(application, usernameKey, securePasswordKey);
            waitForElementToBeVisible(txtService);
            RunLog.specialInfo("Successfully logged in to Salesforce using SOAP API");
            attachScreenShotOfThePageToAllureReport("Login successful!");
        } catch (Exception e) {
            logErrorAndTakeScreenshot("Failed to log in to Salesforce using SOAP API. Please verify credentials.", "loginToSalesforce()", e.getMessage());
        }

    }

    public String errorMessage = """
            Failed to log in to Salesforce and generate a session ID. This could be due to one or more of the following reasons:
            \s
            - Invalid Salesforce credentials: The username or password provided may be incorrect. Please verify that you're using the correct credentials.
            - Incorrect SOAP API URL: The URL used to connect to the Salesforce SOAP API may be incorrect. Please verify that it matches the endpoint specified in your Salesforce WSDL file.
            - Network issues: There may be network issues preventing the connection to Salesforce. Please check your network connection and try again.
            - Salesforce server issues: There may be issues with the Salesforce server. Please check the Salesforce status page and try again later.
            - Unexpected error: An unexpected error occurred. Please check the stack trace for more details.

            The login process involves the SalesforceConnection and SFDCLogin classes, and the methods loginToSalesforce(), login(), getSessionId(), and getConnection() in the SFDCLogin class.""";

    public void login(String applicationName, String username, String password) {
        try {
            Thread getSessionIdThread=new Thread(() -> {
                getSessionId(username, password, applicationName);
            });
            getSessionIdThread.start();
            String url = environment.getValue(applicationName);
            RunLog.info("CRM URL: " + url);
            getSessionIdThread.join();
            if (sessionId != null) {
                RunLog.info("Session generated");
                RunLog.info("Constructing Login URL from API response...");
                String baseUrl = url + sessionId;
                RunLog.info("Login URL successfully constructed: " + baseUrl);
                RunLog.info("Attempting to navigate to the constructed Login URL...");
                navigateToSalesforceLoginUrl(baseUrl);
                waitForPageLoad(driver);
            } else {
                Allure.addAttachment("Salesforce Login Error Information", "text/plain", errorMessage);
                TestAsserts.fail("Session ID is null. Unable to construct URL.");
            }
        } catch (Exception e) {
            RunLog.error("SOAP API Salesforce Login Failed. Exception: " + e.getMessage());
        }
    }

    public void getSessionId(String username, String password, String applicationName) {
        writeLock.lock();
        try {
            RunLog.info("Sending request to Salesforce API...");
            sessionId = getConnection(username, password, applicationName).createNewSession();
            RunLog.info("Received response from Salesforce API");
        } catch (Exception e) {
            RunLog.error("Error: No response received from Salesforce API. Exception: " + e.getMessage());
            RunLog.error("Possible reasons: Connectivity issues, invalid request, or server errors.");
        } finally {
            writeLock.unlock();
        }
    }

    private SalesforceConnection getConnection(String username, String password, String applicationName) {
        String sessionURL = null;
        if(applicationName.equalsIgnoreCase("CRM")) {
            sessionURL ="CRMSessionURL";
        } else {
            RunLog.error("Invalid application name provided: " + applicationName + ". Please provide a valid application name.");
        }
        con = new SalesforceConnection(environment.getValue(username),
                PasswordDecrypt.decrypt_Password(environment.getValue(password)),
                environment.getValue(sessionURL));
        return con;
    }

}
