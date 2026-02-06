package base;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.message.Message;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class JSONLog {
    private static final Logger logger = LogManager.getLogger("JSONLog.response");

    public static void info(String json) {
        logger.info(new ColoredMessage(json));
    }

    private static class ColoredMessage implements Message {
        private final String json;

        public ColoredMessage(String json) {
            this.json = json;
        }

        @Override
        public String getFormattedMessage() {
            return coloredOutput(json);
        }

        @Override
        public String getFormat() {
            return json;
        }

        @Override
        public Object[] getParameters() {
            return new Object[]{};
        }

        @Override
        public Throwable getThrowable() {
            return null;
        }
    }

    public static String coloredOutput(String json) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_COLOR = "\u001B[93m";
        StringBuilder coloredJson = new StringBuilder();
        String[] lines = json.split("\n");
        for (String line : lines) {
            int colonIndex = line.indexOf(':');
            if (colonIndex != -1) {
                String key = line.substring(0, colonIndex + 1);
                String value = line.substring(colonIndex + 1);
                coloredJson.append(ANSI_COLOR).append(key).append(ANSI_RESET).append(value).append("\n");
            } else {
                coloredJson.append(line).append("\n");
            }
        }
        // Remove the last newline
        if (coloredJson.length() > 0) {
            coloredJson.setLength(coloredJson.length() - 1);
        }
        return coloredJson.toString();
    }

    // Please note: The methods defined below this line are primarily for formatting purposes and are not critical to the main functionality.
    @SuppressWarnings("unchecked")
    public String formatDataFromSalesforce(ArrayList<HashMap<String, String>> output) {
        JSONObject jsonObject = new JSONObject();
        // Iterate over each HashMap in the ArrayList
        for (HashMap<String, String> map : output) {
            // Extract the "attributes" key-value pair
            Object attributesObj = map.get("attributes");
            if (attributesObj instanceof HashMap) {
                HashMap<String, Object> attributes = (HashMap<String, Object>) attributesObj;
                // Add the "type" field to the map
                map.put("type", (String) attributes.get("type"));
            }
            // Remove the "attributes" key-value pair
            map.remove("attributes");
            // Add the remaining key-value pairs to the JSONObject
            for (String key : map.keySet()) {
                jsonObject.put(key, map.get(key));
            }
        }
        return jsonObject.toString(4);
    }

}
