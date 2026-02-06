package Test101.Factory.ComponentFactory;

import Test101.Components.*;
import Test101.Components.BoBo.*;
import Test101.Components.BoBoTaxExempt.BoBoTaxExemptComponent;

import Test101.Components.Random.FlipkartSearchComponent;
import Test101.Components.SFDC.*;

public class MyComponentFactory {

    public Common common() { return new Common(); }
    public SearchMe searchMe() { return new SearchMe(); };
    public SFDCLogin sfdcLogin() { return new SFDCLogin(); }
    public AccountDetails accountDetails() { return new AccountDetails(); }
    public CreateAccount createAccount() { return new CreateAccount(); }
    public CreateContact createContact() { return new CreateContact(); }

    public BoBoEnrollmentComponent boBoEnrollment() { return new BoBoEnrollmentComponent(); }
    public BoBoCancellationComponent boBoCancellation() { return new BoBoCancellationComponent(); }
    public BoBoAccountProgram boBoAccountProgram() { return new BoBoAccountProgram(); }
    public BoBoTaxExemptComponent boBoTaxExempt() { return new BoBoTaxExemptComponent(); }
    public FlipkartSearchComponent flipkartSearch() { return new FlipkartSearchComponent(); }

}
