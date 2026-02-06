package Test101.Pages;

import Test101.Components.BoBo.BoBoCancellationComponent;
import Test101.Factory.ComponentFactory.MyComponentFactory;

public class BoBoCancellationPage extends MyComponentFactory {

    public BoBoCancellationComponent boBoCancellationComponent() { return boBoCancellation(); }

}
