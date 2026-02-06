package base;

import java.util.Iterator;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.testng.Assert;

public class TestAsserts {
    public TestAsserts() {
    }

    public static void fail(String message) {
        Assertions.fail(message);
    }

    public static void fail(String message, Throwable exp) {
        Assertions.fail(message + "  " + exp.toString(), exp);
    }

    public static void assertEquals(Integer actualValue, Integer expectedValue) {
        Assert.assertEquals(actualValue, expectedValue);
    }

    public static void assertTextContainsIgnoreCase(String actualText, String expectedText) {
        Assert.assertTrue(actualText.trim().toLowerCase().contains(expectedText.trim().toLowerCase()), "expected text " + expectedText + " is not present in the actual text " + actualText);
    }

    public static void assertEquals(String actualText, String expectedText) {
        Assert.assertTrue(actualText.trim().equalsIgnoreCase(expectedText.trim()), "expected text " + expectedText + " does not match the actual text " + actualText);
    }

    public static void validateTextNotPresentInList(String textToBeValidated, List<String> listOfText) {
        Iterator var2 = listOfText.iterator();

        while(var2.hasNext()) {
            String text = (String)var2.next();
            if (text.equalsIgnoreCase(textToBeValidated)) {
                fail("" + textToBeValidated + " is present in the list of text :" + listOfText + " which is not expected.");
            }
        }

    }

    public static void assertTextContains(String actualText, String expectedText) {
        Assert.assertTrue(actualText.contains(expectedText), "expected text " + expectedText + " is not present in the actual text " + actualText);
    }

    public static void assertCondition(boolean conditionIndicator, boolean conditionToCheck, String customErrorMessage) {
        if (conditionIndicator) {
            Assert.assertTrue(conditionToCheck, customErrorMessage);
        } else {
            Assert.assertFalse(conditionToCheck, customErrorMessage);
        }

    }
}
