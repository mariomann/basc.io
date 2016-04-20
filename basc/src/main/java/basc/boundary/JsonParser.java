package basc.boundary;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

@Component
public class JsonParser {
//    public String parseString(String json, String element) {
//        return parseValue(json, element);
//    }

    public Integer parseInteger(String json, String element) {
        return Integer.parseInt(parseString(json, element));
    }

    public String parseString(String json, String element) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(json);
            return String.valueOf(jsonObject.get(element));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
