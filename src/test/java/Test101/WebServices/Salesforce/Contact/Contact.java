package Test101.WebServices.Salesforce.Contact;

import Test101.utils.SalesforceClient;
import Test101.utils.StorageMap;
import base.JSONLog;
import base.RunLog;
import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

public class Contact {

    private static final Faker faker = new Faker();

    public String create(final String sfAccountId) {
        RequestSpecification request = new SalesforceClient().request().body(requestBody(sfAccountId).toString());
        return sendRequest(request);
    }

    private String sendRequest(final RequestSpecification request) {
        Response response = request.post("/sobjects/Contact/");
        if ((response.getStatusCode() == 201 || response.jsonPath().get("success").equals(true))) {
            RunLog.specialInfo("Contact created successfully in salesforce: " + response.getStatusLine());
        } else {
            RunLog.error("Contact is not created: " + response.getStatusLine() + " Response: " + response.getBody().prettyPrint());
        }
        return response.jsonPath().get("id");
    }

    private JSONObject requestBody(final String account) {
        JSONObject requestParams = new JSONObject();
        setupData();
        requestParams.put("LastName", StorageMap.getLocalKeyValue("LastName"));
        requestParams.put("FirstName", StorageMap.getLocalKeyValue("FirstName"));
        requestParams.put("Email", StorageMap.getLocalKeyValue("Email"));
        requestParams.put("AccountId", account);
        requestParams.put("Level__c", "Primary");
        requestParams.put("MobilePhone", "9876512340");
        requestParams.put("Languages__c", "Hindi, English");

        String jsonString = requestParams.toString();
        JSONObject json = new JSONObject(jsonString);
        String prettyPrintedJson = json.toString(4);
        JSONLog.info("Contact Request: " + prettyPrintedJson);

        requestParams.put("Description", "Created using API Automation");
        return requestParams;
    }

    private void setupData() {
        StorageMap.setLocalKeyValue("FirstName", faker.name().firstName());
        StorageMap.setLocalKeyValue("LastName", faker.name().lastName());
        StorageMap.setLocalKeyValue("Email", faker.internet().emailAddress());
    }

    public String updateAccountOnContact(final String accountId, final String contactId) {
        JSONObject requestParams = new JSONObject();
        requestParams.put("AccountId", accountId);
        String token = new SalesforceClient().getToken().get("Access_Token");
        RequestSpecification request = new SalesforceClient().requestWithSession(token).body(requestParams.toString());
        return sendRequestForAccountUpdate(request, contactId);
    }

    private String sendRequestForAccountUpdate(final RequestSpecification request, final String contactRecordId) {
        Response response = request.patch("/sobjects/Contact/" + contactRecordId);
        if (response.getStatusCode() == 204) {
            RunLog.info("Contact updated successfully in salesforce: " + response.getStatusLine());
        } else {
            RunLog.error("Contact is not updated: " + response.getStatusLine());
        }
        return contactRecordId;
    }

}
