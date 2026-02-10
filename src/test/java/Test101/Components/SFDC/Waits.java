package Test101.Components.SFDC;

import java.time.Duration;

import base.TestAsserts;
import Test101.Components.Common;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Waits {

    private static final int DEFAULT_WAIT_SECONDS = 30;

    public static WebElement waitForElement(By inputElement, WAIT_CONDITIONS condition) throws Exception {
        return waitForElement(inputElement, condition, DEFAULT_WAIT_SECONDS);
    }

    public static WebElement waitForElement(By inputElement, WAIT_CONDITIONS condition, int WaitTime) throws Exception {
        Wait wait_custom = new WebDriverWait(Common.driver, Duration.ofSeconds((long) WaitTime));

        WebElement elementToReturn;
        try {
            switch (condition) {
                case CLICKABLE ->
                        elementToReturn = (WebElement) wait_custom.until(ExpectedConditions.elementToBeClickable(inputElement));
                case VISIBLE ->
                        elementToReturn = (WebElement) wait_custom.until(ExpectedConditions.visibilityOfElementLocated(inputElement));
                case AVAILABLE ->
                        elementToReturn = (WebElement) wait_custom.until(ExpectedConditions.presenceOfElementLocated(inputElement));
                default -> {
                    elementToReturn = null;
                    TestAsserts.fail("Condition for wait is not supported");
                }
            }
        } catch (TimeoutException var7) {
            elementToReturn = null;
            TestAsserts.fail("Timed out waiting for element - " + inputElement, var7);
        }

        return elementToReturn;
    }

    // ... other methods ...

    public static enum WAIT_CONDITIONS {
        CLICKABLE,
        VISIBLE,
        AVAILABLE;

        private WAIT_CONDITIONS() {
        }
    }


}

