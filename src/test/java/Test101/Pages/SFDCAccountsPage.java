package Test101.Pages;

import Test101.Factory.ComponentFactory.MyComponentFactory;

import java.util.Map;

public class SFDCAccountsPage extends MyComponentFactory {

    public void navigateToNewAccountPage() {createAccount().navigateToNewAccountPage();}
    public void createNewAccountRecord(Map<String,String> fillData) { createAccount().createAccountRecord(fillData); }
    public void validateNewAccountRecord(Map<String,String> fillData) { createAccount().validateNewAccountData(fillData);}
}
