package Test101.Pages;

import Test101.Components.SFDC.AccountDetails;
import Test101.Factory.ComponentFactory.MyComponentFactory;

public class SFDCAccountDetailsPage extends MyComponentFactory {

    public AccountDetails accountDetail() { return accountDetails(); }

    public void navigateToAccount(String accountName) { accountDetails().navigateToAccount(accountName); }
    public void userNavigateToAccount(String accountId) { accountDetails().userNavigateToAccount(accountId); }
    public void navigateToTab(String tabName) { accountDetails().navigateToTab(tabName); }
    public void validateAccountDetails() { accountDetails().validateAccountData(); }
}
