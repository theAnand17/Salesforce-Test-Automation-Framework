package Test101.Pages;

import Test101.Factory.ComponentFactory.MyComponentFactory;

public class GoogleHomePage extends MyComponentFactory {

    public void googleSearch(String searchText) {searchMe().searchMeOnGoogle(searchText);}
    public void validateSearchResult(String screenshotName) {searchMe().validateSearch(screenshotName);}
}
