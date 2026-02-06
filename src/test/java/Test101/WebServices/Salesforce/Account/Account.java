package Test101.WebServices.Salesforce.Account;

import Test101.utils.SalesforceClient;
import Test101.utils.StorageMap;
import base.RunLog;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

public class Account {

    private static final Faker faker = new Faker();

    public String create() {
        JSONObject requestParams = createRequest();
        RequestSpecification request = new SalesforceClient().request().body(requestParams.toString());
        return sendRequest(request);
    }
    private JSONObject createRequest() {
        setupData();
        JSONObject requestParams = new JSONObject();
        basicDetailsRequestBody(requestParams);
        requestParams.put("OwnerId", "0055j000009AsfUAAS");
        requestParams.put("Description", "Created using API Automation");
        return requestParams;
    }

    private String sendRequest(final RequestSpecification request) {
        Response response = request.post("/sobjects/Account/");
        String accountId=null;
        //String redirectUrl = response.getHeader("Location");
        if ((response.getStatusCode() == 201 || response.jsonPath().get("success").equals(true))) {
            RunLog.specialInfo("Account created successfully in salesforce: " + response.getStatusLine());
            accountId=response.jsonPath().get("id");
            RunLog.info("Salesforce Account Id created: "+accountId);
        } else {
            RunLog.error("Account is not created: " + response.getStatusLine());
            RunLog.error("Account creation error response: " + response.asString());
        }
        return accountId;
    }
    private void basicDetailsRequestBody(JSONObject requestParams) {
        requestParams.put("Name", StorageMap.getLocalKeyValue("AccountName"));
        requestParams.put("BillingStreet", StorageMap.getLocalKeyValue("StreetAddress"));
        requestParams.put("ShippingStreet", StorageMap.getLocalKeyValue("StreetAddress"));
        requestParams.put("BillingCity", StorageMap.getLocalKeyValue("City"));
        requestParams.put("ShippingCity", StorageMap.getLocalKeyValue("City"));
        requestParams.put("BillingState", StorageMap.getLocalKeyValue("State"));
        requestParams.put("ShippingState", StorageMap.getLocalKeyValue("State"));
        requestParams.put("BillingCountry", StorageMap.getLocalKeyValue("Country"));
        requestParams.put("ShippingCountry", StorageMap.getLocalKeyValue("Country"));
        requestParams.put("BillingPostalCode", StorageMap.getLocalKeyValue("ZipCode"));
        requestParams.put("ShippingPostalCode", StorageMap.getLocalKeyValue("ZipCode"));
        requestParams.put("AccountNumber", StorageMap.getLocalKeyValue("AccountNumber"));
        requestParams.put("Phone", StorageMap.getLocalKeyValue("PhoneNumber"));
        requestParams.put("Industry", StorageMap.getLocalKeyValue("Industry"));
        requestParams.put("Ownership", StorageMap.getLocalKeyValue("Ownership"));
        requestParams.put("Type", StorageMap.getLocalKeyValue("Type"));
    }

    private void setupData() {
        StorageMap.setLocalKeyValue("AccountName", "TestAutomation " + faker.company().name());
        StorageMap.setLocalKeyValue("StreetAddress", faker.address().streetAddress());
        StorageMap.setLocalKeyValue("City", faker.address().city());
        StorageMap.setLocalKeyValue("State", faker.address().state());
        StorageMap.setLocalKeyValue("Country", faker.address().country());
        StorageMap.setLocalKeyValue("ZipCode", faker.address().zipCode());
        StorageMap.setLocalKeyValue("AccountNumber", String.valueOf(faker.number().randomNumber(8, true)));
        StorageMap.setLocalKeyValue("PhoneNumber", "9876512340");
        StorageMap.setLocalKeyValue("Industry", "Engineering");
        StorageMap.setLocalKeyValue("Ownership", "Private");
        StorageMap.setLocalKeyValue("Type", "Prospect");
    }

}
