package Test101.utils;

import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LocalStorageMap<K, V> {
    private final ThreadLocal<HashMap<K, V>> keyStore = new ThreadLocal<HashMap<K, V>>() {
        protected HashMap<K, V> initialValue() {
            return new HashMap<>();
        }
    };

    public LocalStorageMap() {
    }

    public V getLocalKeyValue(K key) {
        return (V)((HashMap)keyStore.get()).get(key);
    }

    public void setLocalKeyValue(K key, V value) {
        ((HashMap)keyStore.get()).put(key, value);
    }

    public void setAllLocalKeyValue(Map<String, String> data) {
        ((HashMap)keyStore.get()).putAll(data);
    }

    public void setAllLocalKeyValue(JSONObject data) {
        Map<K, V> result = (Map)(new Gson()).fromJson(data.toString(), Map.class);
        ((HashMap)keyStore.get()).putAll(result);
    }

    public void resetKeyStore() {
        ((HashMap)keyStore.get()).clear();
    }
}