package Test101.utils;

import base.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SalesforceClient {
    private String accessToken;
    private String instanceUrl;
    private String resourceUrl;
    private String clientId;
    private String clientSecret;
    private String username;
    private String password;

    PropertiesLoader loader = new PropertiesLoader();
    Properties properties = loader.loadProperties();

    private static final String SalesforceBaseURL = new Environment().getValue("SalesforceBaseURL");

    public SalesforceClient() {
        initializeCredentials();
        setAccessToken();
    }

    private void initializeCredentials() {
        String env = System.getProperty("env");

        if ("Test".equalsIgnoreCase(env)) {
            initializeCredentialsForEnv(properties, "client_id", "client_secret", "username", "password", "TestUrl", "resourceUrl");
        } else {
            RunLog.error("Invalid or unspecified environment. Please set '-Denv=Test' as a system property.");
        }

        RunLog.info("URL to get Token: " + properties.getProperty("TestUrl"));
    }

    private void initializeCredentialsForEnv(Properties properties, String clientIdKey, String clientSecretKey,
                                             String usernameKey, String passwordKey, String urlKey, String resourceUrlKey) {
        clientId = properties.getProperty(clientIdKey);
        clientSecret = properties.getProperty(clientSecretKey);
        username = properties.getProperty(usernameKey);
        password = PasswordDecrypt.decrypt_Password(properties.getProperty(passwordKey));
        instanceUrl = properties.getProperty(urlKey);
        resourceUrl = properties.getProperty(resourceUrlKey);

        RunLog.info("Credentials initialized for environment: " + System.getProperty("env"));
    }

    private void setAccessToken() {
        try {
            RestAssured.baseURI = instanceUrl; // Set baseURI before sending the request

            RequestSpecification request = RestAssured.given();
            request.header("Content-Type", "application/x-www-form-urlencoded");
            request.formParam("grant_type", "password");
            request.formParam("client_id", clientId);
            request.formParam("client_secret", clientSecret);
            request.formParam("username", username);
            request.formParam("password", password);

            Response response = request.post("/services/oauth2/token");
            RunLog.info("Token Set Response: " + response.getStatusLine());

            //Print Response Body in Console
            String jsonString = response.getBody().asString();
            JSONObject json = new JSONObject(jsonString);
            String prettyPrintedJson = json.toString(4);
            JSONLog.info("Token Set Response JSON: " + prettyPrintedJson);

            accessToken = response.jsonPath().get("access_token");
            instanceUrl = response.jsonPath().get("instance_url");

            RunLog.info("Access Token has been set successfully");
            RunLog.info("Instance URL: " + instanceUrl);
        } catch (Exception e) {
            RunLog.error("Error setting access token: " + e.getMessage());
            throw new RuntimeException("Error setting access token", e);
        }
    }


    public Map<String, String> getToken() {
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("Access_Token", accessToken);
        tokenMap.put("Instance_URL", instanceUrl);
        tokenMap.put("resourceUrl", resourceUrl);
        return tokenMap;
    }

    public ArrayList<HashMap<String, String>> query(String soqlQuery) {
        RestAssured.baseURI = instanceUrl;
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(accessToken);

        RunLog.info("SOQL Query: " + soqlQuery);
        Response response = request.get(resourceUrl + soqlQuery);

        if (response.jsonPath().get("totalSize").equals(0)) {
            RunLog.info("No data available for query: " + soqlQuery);
            return new ArrayList<>();
        } else {
            ArrayList<HashMap<String, String>> output = response.jsonPath().get("records");
            RunLog.info("Data from Salesforce: " + output.toString());
            return output;
        }
    }

    public ArrayList<HashMap<String, String>> getResult(String soqlQuery) {
        RestAssured.baseURI = instanceUrl;
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(accessToken);

        RunLog.info("SOQL Query: " + soqlQuery);
        Response response = request.get(resourceUrl + soqlQuery);

        if (response.jsonPath().get("totalSize").equals(0)) {
            RunLog.info("No test data available!");
            return new ArrayList<>();
        } else {
            ArrayList<HashMap<String, String>> output = response.jsonPath().get("records");
            //RunLog.info("Data from Salesforce: " + output.toString());
            //Please remove the following log statement if it causes unnecessary errors.
            String dataFromSalesforce = new JSONLog().formatDataFromSalesforce(output);
            JSONLog.info("Data from Salesforce: " + dataFromSalesforce);

            return output;
        }
    }

    public RequestSpecification request() {
        Map<String, String> token = getToken();
        StorageMap.setLocalKeyValue("Access_Token", token.get("Access_Token"));
        return requestWithSession(token.get("Access_Token"));
    }

    public RequestSpecification requestWithSession(String tokenId) {
        String env = System.getProperty("env");
        if ("Test".equalsIgnoreCase(env)) {
            RestAssured.baseURI= SalesforceBaseURL;
        } else {
            RunLog.error("Invalid or unspecified environment. Please set '-Denv=Test' as a system property.");
        }
        return RestAssured.given()
                .relaxedHTTPSValidation()
                .header("Authorization","Bearer "+tokenId)
                .header("Content-Type","application/json");
    }

}
