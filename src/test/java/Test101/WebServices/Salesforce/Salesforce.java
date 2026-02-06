package Test101.WebServices.Salesforce;

import Test101.WebServices.Salesforce.Account.Account;
import Test101.WebServices.Salesforce.Contact.Contact;

public class Salesforce {

    public Account account() { return new Account(); }
    public Contact contact() { return new Contact(); }

}
