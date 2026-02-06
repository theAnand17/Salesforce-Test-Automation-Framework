package Test101.utils;

import base.RunLog;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class YamlReader {
    private String sectionRootName;
    private String filePath;
    private final Config config;

    public YamlReader(){ config=new Config();}

    public YamlReader(String filePath){
        this.filePath=filePath;
        config=new Config();
    }

    public YamlReader(String filePath, String sectionRootNameInYaml){
        this.filePath=filePath;
        this.sectionRootName=sectionRootNameInYaml;
        config=new Config();
        read();
    }

    public YamlReader read() {
        Yaml yaml = new Yaml();
        try {
            InputStream file = new FileInputStream(filePath);
            Object environmentTmp = yaml.load(file);
            if (!(environmentTmp instanceof Map)) {
                throw new IOException("File not formatted correctly");
            }

            Map<String, Object> environment = (Map)environmentTmp;
            Map<String, Object> selected_environment = (Map)environment.get(sectionRootName);
            config.init(selected_environment);
        } catch (Exception e) {
            RunLog.error("Issue in setting up the Application environment properties");
            RunLog.error("Exception: " + e.getMessage());
        }
        return this;
    }

    public YamlReader read(String sectionRootNameInYaml) {
        Yaml yaml = new Yaml();
        try {
            InputStream file = new FileInputStream(filePath);
            Object environmentTmp = yaml.load(file);
            if (!(environmentTmp instanceof Map)) {
                throw new IOException("File not formatted correctly");
            }

            Map<String, Object> environment = (Map)environmentTmp;
            Map<String, Object> selected_environment = (Map)environment.get(sectionRootNameInYaml);
            config.init(selected_environment);
            RunLog.info("App Prop init called");
        } catch (Exception e) {
            RunLog.error("Issue in setting up the Application environment properties");
            RunLog.error("Exception: " + e.getMessage());
        }
        return this;
    }

    public YamlReader read(String filePathName, String sectionRootNameInYamlFile) {
        Yaml yaml = new Yaml();
        try {
            InputStream file = new FileInputStream(filePathName);
            Object environmentTmp = yaml.load(file);
            if (!(environmentTmp instanceof Map)) {
                throw new IOException("File not formatted correctly");
            }
            Map<String, Object> environment = (Map)environmentTmp;
            Map<String, Object> selected_environment = (Map)environment.get(sectionRootNameInYamlFile);
            config.init(selected_environment);
            RunLog.info("App Prop init called");
        } catch (Exception e) {
            RunLog.error("Issue in setting up the Application environment properties");
            RunLog.error("Exception: " + e.getMessage());
        }
        return this;
    }

    public String get(String key){
        return config.getValue(key);
    }

}