package Test101.Components.SFDC;

import Test101.utils.SalesforceClient;
import Test101.utils.YamlReader;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Supplier;

public class AccountDataFactory {
    private static final YamlReader query = new YamlReader("src/test/resources/TestParams/soqlqueries.yaml", "Account");
    private static final SalesforceClient soqlQuery = new SalesforceClient();

    private static final Map<String, Supplier<String>> MAP = new HashMap<>();

    private static final Supplier<String> accountWithoutContact =
            () -> soqlQuery.getResult(query.get("accountWithoutContact")).get(0).get("Id");
    private static final Supplier<String> boboEnrolledAccount =
            () -> soqlQuery.getResult(query.get("boboEnrolledAccount")).get(0).get("Id");
    private static final Supplier<String> accountWithoutTaxExemption =
            () -> soqlQuery.getResult(query.get("accountWithoutTaxExemption")).get(0).get("Id");

    static {
        MAP.put("Account Without Contact", accountWithoutContact);
    }
    static {
        MAP.put("BoBo Enrolled Account", boboEnrolledAccount);
    }
    static {
        MAP.put("Account Without Tax Exemption", accountWithoutTaxExemption);
    }

    public String getIdFor(String accountDataCriteria) {
        return MAP.get(accountDataCriteria).get();
    }

}
