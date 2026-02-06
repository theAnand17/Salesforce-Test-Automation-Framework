package Test101.utils;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

public class StorageMap {
    private static final ThreadLocal<HashMap<String, Object>> keyStore = ThreadLocal.withInitial(HashMap::new);

    public StorageMap() {
    }

    public static String getLocalKeyValue(String key) {
        return (String)((HashMap)keyStore.get()).get(key);
    }

    public static Object getLocalKeyValueAsObject(String key) {
        return ((HashMap)keyStore.get()).get(key);
    }

    public static boolean isContains(String key) {
        return ((HashMap)keyStore.get()).containsKey(key);
    }

    public static void setLocalKeyValue(String key, Object value) {
        ((HashMap)keyStore.get()).put(key, value);
    }

    public static void setAllLocalKeyValue(Map<String, String> data) {
        ((HashMap)keyStore.get()).putAll(data);
    }

    public static void setAllLocalKeyValueObjects(Map<String, Object> data) {
        ((HashMap)keyStore.get()).putAll(data);
    }

    public static void setAllLocalKeyValue(JSONObject data) {
        Map<String, Object> result = (Map)(new Gson()).fromJson(data.toString(), Map.class);
        ((HashMap)keyStore.get()).putAll(result);
    }

    public static void resetKeyStore() {
        ((HashMap)keyStore.get()).clear();
    }

    public static Map<String, String> getAllLocalKeyValues() {
        Set<Map.Entry<String, Object>> allData = ((HashMap)keyStore.get()).entrySet();
        Map<String, String> allKeyValues = new HashMap();
        Iterator var2 = allData.iterator();

        while(var2.hasNext()) {
            Map.Entry<String, Object> map1 = (Map.Entry)var2.next();
            allKeyValues.put((String)map1.getKey(), map1.getValue().toString());
        }

        return allKeyValues;
    }

    public static Map<String, Object> getAllLocalKeyValuesAsObject() {
        return (Map)keyStore.get();
    }
}
