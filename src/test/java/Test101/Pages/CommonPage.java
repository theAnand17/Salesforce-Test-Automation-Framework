package Test101.Pages;

import Test101.Factory.ComponentFactory.MyComponentFactory;
import io.cucumber.java.Scenario;

import java.time.temporal.Temporal;

public class CommonPage extends MyComponentFactory {

    public void launchApplication(String Application, String browser) {common().navigateToApplicationInBrowser(Application, browser);}
    public void navigateToURL(String url, String browser) {common().navigateToURLInBrowser(url, browser);}
    public void navigateToWebsite(String websiteName) {common().navigateToWebsite(websiteName);}
    public void quitBrowser() {common().closeAndQuitBrowser();}
    public void tearDownForTestFailure(Scenario scenario, Temporal testStartTime) {common().tearDownForTestFailure(scenario, testStartTime);}
    public void setUp() {common().setUp();}
    public void tearDown(Temporal testStartTime) {common().tearDown(testStartTime);}

}
