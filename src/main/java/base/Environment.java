package base;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Slf4j
public class Environment {
    private Map<String, Object> data;

    public Environment() {
        String environment = System.getProperty("environment");
        Yaml yaml = new Yaml();
        try (InputStream inputStream = new FileInputStream("environment.yaml")) {
            Map<String, Map<String, Object>> yamlData = yaml.loadAs(inputStream, Map.class);
            data = yamlData.get(environment);

        } catch (IOException e) {
            RunLog.error("No data found for environment: " + environment, e);
        }
    }

    public String getValue(String key) {
        return (String) data.get(key);
    }
}
