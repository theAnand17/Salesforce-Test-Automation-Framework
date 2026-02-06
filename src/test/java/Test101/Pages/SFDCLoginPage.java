package Test101.Pages;

import Test101.Factory.ComponentFactory.MyComponentFactory;
import io.cucumber.java.en.When;

public class SFDCLoginPage extends MyComponentFactory {

    public void loginToSFDCAsUser(String user) {sfdcLogin().loginToSalesforceFromLoginPage(user);}
    public void loginToSalesforce(String user) {sfdcLogin().loginToSalesforce(user);}
    public void logoutFromSFDC() {sfdcLogin().logoutFromSFDC();}
}
