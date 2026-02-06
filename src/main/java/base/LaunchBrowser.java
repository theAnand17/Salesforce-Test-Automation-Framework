package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;

@Getter
public class LaunchBrowser {

    public WebDriver driver;
    PrintStream originalErr;

    PropertiesLoader loader = new PropertiesLoader();
    Properties properties = loader.loadProperties();
    private final String headlessModeValue = properties.getProperty("headlessMode");

    public LaunchBrowser(String browserName) {
        suppressCDPVersionFinderError();
        // Set the appropriate system property based on the chosen browser
        switch (browserName) {
            case "Chrome" -> driver = setupChromeDriver();
            case "Edge" -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions options = new EdgeOptions();
                if ("true".equalsIgnoreCase(headlessModeValue)) {
                    options.addArguments("--headless=old"); // Run Edge in headless mode (if supported)
                    options.addArguments("--disable-gpu"); // Disable the GPU to prevent some issues
                    RunLog.specialInfo("Headless mode preference set to true. Edge will launch in headless mode.");
                } else if ("false".equalsIgnoreCase(headlessModeValue)) {
                    RunLog.specialInfo("Headless mode is disabled. Edge will run in normal mode.");
                } else {
                    RunLog.warn("Invalid value for headlessMode property: " + headlessModeValue + ". Using non-headless mode by default.");
                }
                // Create EdgeDriver instance with EdgeOptions
                driver = new EdgeDriver(options);
            }
            default -> {
                RunLog.warn("Unsupported browser: " + browserName + ". Switching to Chrome by default.");
                driver = setupChromeDriver();
            }
        }
        // Restore the original stderr
        System.setErr(originalErr);
    }

    private WebDriver setupChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if ("true".equalsIgnoreCase(headlessModeValue)) {
            options.addArguments("--headless=old"); // Run Chrome in headless mode
            options.addArguments("--disable-gpu"); // Disable the GPU to prevent some issues
            RunLog.specialInfo("Headless mode preference set to true. Chrome will launch in headless mode.");
        } else if ("false".equalsIgnoreCase(headlessModeValue)) {
            RunLog.specialInfo("Headless mode preference set to false. Chrome will launch normally.");
        } else {
            RunLog.warn("Invalid value for headlessMode property: " + headlessModeValue + ". Using non-headless mode by default.");
        }
        return new ChromeDriver(options);
    }

    public void getUrl(String url) {
        // Open the specified URL
        driver.get(url);
        driver.manage().window().maximize();
    }

    public void suppressCDPVersionFinderError() {
        // Save the original stderr
        originalErr = System.err;
        // Redirect stderr to a custom PrintStream
        System.setErr(new PrintStream(new OutputStream() {
            public void write(int b) {
                // NO-OP
            }
        }));
    }

}

