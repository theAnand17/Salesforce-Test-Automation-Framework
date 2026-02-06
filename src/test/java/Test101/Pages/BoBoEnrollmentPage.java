package Test101.Pages;

import Test101.Components.BoBo.BoBoEnrollmentComponent;
import Test101.Factory.ComponentFactory.MyComponentFactory;

public class BoBoEnrollmentPage extends MyComponentFactory {

    public BoBoEnrollmentComponent boBoEnrollmentComponent() { return boBoEnrollment(); }

}
