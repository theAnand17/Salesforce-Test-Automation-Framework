package Test101.Components.SFDC;

import base.RunLog;
import Test101.Components.Common;
import Test101.Components.Element;
import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public class Logout extends Common {
    String locatorsPageName = "SFDCLocators";
    Wait<WebDriver> wait;

    @FindBy
    private By loginForm;

    public Logout() {
        try {
            this.wait = wait(30);
            assignLocatorPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void assignLocatorPage() {
        loginForm = Element.returnBrowserElementFromRepo(locatorsPageName, "SFDCLogoutComponent", "loginForm");
    }

    public Logout get() {
        try {
            getUserIcon();
            getLogoutLink();
        } catch (AssertionError | Exception e) {
            RunLog.error("Not Able to logout from application");
        }
        return this;
    }

    private void getUserIcon() {
        try {
            jse.executeScript("document.querySelector('div.forceEntityIcon img.noicon').click()");
        } catch (AssertionError | Exception e) {
            RunLog.error("Not able to get User Icon");
        }
    }

    private void getLogoutLink() {
        try {
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
            try {
                jse.executeScript("document.querySelector('a.logout').click()");
                wait(30).until(d -> d.findElement(loginForm).isDisplayed());
            } catch (Exception e) {
                jse.executeScript("document.querySelector('div.forceEntityIcon img.noicon').click()");
                Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
                jse.executeScript("document.querySelector('a.logout').click()");
                wait(30).until(d -> d.findElement(loginForm).isDisplayed());
            }
            Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
        } catch (AssertionError | Exception e) {
            RunLog.error("Not able to get Logout link");
        }
    }

}
