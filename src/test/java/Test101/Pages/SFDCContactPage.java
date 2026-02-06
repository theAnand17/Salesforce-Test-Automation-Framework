package Test101.Pages;

import Test101.Components.SFDC.CreateContact;
import Test101.Factory.ComponentFactory.MyComponentFactory;

public class SFDCContactPage extends MyComponentFactory {

    public CreateContact createNewContact() { return createContact(); }
}
