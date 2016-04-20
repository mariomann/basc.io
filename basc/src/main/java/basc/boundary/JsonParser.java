package basc.boundary;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonParser {
    public String parseString(String json, String element) {
        return parseValue(json, element);
    }

    public Integer parseInteger(String json, String element) {
        return Integer.parseInt(parseValue(json, element));
    }

    private String parseValue(String json, String element) {
        String value = "";

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(json);
            value = String.valueOf(jsonObject.get(element));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return value;
    }
}
