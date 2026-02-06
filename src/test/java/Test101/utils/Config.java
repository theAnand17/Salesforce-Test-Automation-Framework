package Test101.utils;

import java.util.HashMap;
import java.util.Map;

public class Config {

    private Map<String, Object> configMap;

    public void init(Map<String, Object> configMap) {
        this.configMap = configMap;
    }



    public String getValue(String key) {
        return String.valueOf(configMap.get(key));
    }

    public HashMap<String, String> getValueAsObject(String key) {
        return (HashMap)configMap.get(key);
    }

    public void endThreadLocal() {
        configMap.clear();
    }

}