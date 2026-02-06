package Test101.Pages.Random;

import Test101.Components.Random.FlipkartSearchComponent;
import Test101.Factory.ComponentFactory.MyComponentFactory;

public class FlipkartHomePage extends MyComponentFactory {

    public FlipkartSearchComponent flipkartSearchComponent() { return flipkartSearch(); }
}
