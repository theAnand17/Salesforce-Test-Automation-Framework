package Test101.Components;
import org.openqa.selenium.By;
import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class Element {
    private static final String LOCATORS_PATH = "src//test//resources//Locators";

    public static By returnBrowserElementFromRepo(String yamlFileName, String componentName, String elementName) {
        Yaml yaml = new Yaml();
        Map<String, Object> locators;
        try {
            FileInputStream inputStream = new FileInputStream(LOCATORS_PATH + "/" + yamlFileName + ".yaml");
            locators = yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not find YAML file: " + yamlFileName, e);
        }

        Map<String, Object> component = (Map<String, Object>) locators.get(componentName);
        Map<String, Object> element = (Map<String, Object>) component.get(elementName);
        String type = (String) element.get("type");
        String value = (String) element.get("value");

        return switch (type) {
            case "xpath" -> By.xpath(value);
            case "id" -> By.id(value);
            case "name" -> By.name(value);
            case "className" -> By.className(value);
            case "css" -> By.cssSelector(value);
            default -> throw new IllegalArgumentException("Invalid locator type: " + type);
        };
    }
}

