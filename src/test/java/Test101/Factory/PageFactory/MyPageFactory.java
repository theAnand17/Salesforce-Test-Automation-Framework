package Test101.Factory.PageFactory;

import Test101.Components.BoBoTaxExempt.BoBoTaxExemptComponent;
import Test101.Pages.*;
import Test101.Pages.Random.FlipkartHomePage;

public class MyPageFactory {

    public CommonPage commonPage() { return new CommonPage(); }
    public GoogleHomePage googleHomePage() { return new GoogleHomePage(); }
    public SFDCLoginPage sfdcLoginPage() { return new SFDCLoginPage(); }
    public SFDCAccountDetailsPage sfdcAccountDetailsPage() { return new SFDCAccountDetailsPage(); }
    public SFDCAccountsPage sfdcAccountsPage() { return new SFDCAccountsPage(); }
    public SFDCContactPage sfdcContactPage() {return new SFDCContactPage(); }

    public BoBoEnrollmentPage boBoEnrollmentPage() { return new BoBoEnrollmentPage(); }
    public BoBoCancellationPage boBoCancellationPage() { return new BoBoCancellationPage(); }
    public BoBoAccountProgramPage boBoAccountProgramPage() { return new BoBoAccountProgramPage(); }
    public BoBoTaxExemptPage boBoTaxExemptPage() { return new BoBoTaxExemptPage(); }
    public FlipkartHomePage flipkartHomePage() { return new FlipkartHomePage(); }
}
