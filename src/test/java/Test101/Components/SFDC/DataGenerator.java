package Test101.Components.SFDC;

import Test101.Components.Common;
import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Map;

public class DataGenerator extends Common {

    private static final Faker faker = new Faker();

    public static Map<String, String> generateAccountData() {
        Map<String, String> accountData = new HashMap<>();
        accountData.put("AccountName", faker.company().name());
        accountData.put("StreetAddress", faker.address().streetAddress());
        accountData.put("City", faker.address().city());
        accountData.put("State", faker.address().state());
        accountData.put("Country", faker.address().country());
        accountData.put("ZipCode", faker.address().zipCode());
        accountData.put("AccountNumber", String.valueOf(faker.number().randomNumber()));
        return accountData;
    }

    public static Map<String, String> generateContactData() {
        Map<String, String> accountData = new HashMap<>();
        accountData.put("FirstName", faker.name().firstName());
        accountData.put("LastName", faker.name().lastName());
        accountData.put("Phone", faker.phoneNumber().phoneNumber());
        accountData.put("Mobile", faker.phoneNumber().cellPhone());
        accountData.put("Email", faker.internet().emailAddress());
        return accountData;
    }

}
