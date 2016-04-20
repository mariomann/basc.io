package basc.boundary;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonParserTest {

    JsonParser jsonParser = new JsonParser();
    String json = "{\"access_token\": \"AKRPoJuiQyRM4r24FVm51ZLzTjjiof7hNpxArwsKHAPMUEIqZxKqe6mL8LWjRAg0LqAiZsr4SQNVDCbHVzayVKAfuuT1g_I0d8dRqbdLxpoE\",\n" +
                    "\"expires_in\": 3600,\n" +
                    "\"refresh_token\": \"RwrmIDKoMHaTm3d2KyADS4Km6nyGZb70m2xDwW3qzjCGf5QoH_vBhgrcSXMRp6TGCPTZZPl6B1od-WgE6qjIMb9Mhlfnmc6UNuNXjqI4_Rcs\",\n" +
                    "\"scope\": \"accounts=rw balance=rw transactions=rw user=rw payments=rw securities=rw offline submit_payments create_user\",\n" +
                    "\"token_type\": \"Bearer\"}";

    @Test
    public void parseAccessToken() {
        String accessToken = jsonParser.parseString(json, "access_token");

        assertThat(accessToken, is(equalTo("AKRPoJuiQyRM4r24FVm51ZLzTjjiof7hNpxArwsKHAPMUEIqZxKqe6mL8LWjRAg0LqAiZsr4SQNVDCbHVzayVKAfuuT1g_I0d8dRqbdLxpoE")));
    }

    @Test
    public void parseExpiresIn() {
        int expiresIn = jsonParser.parseInteger(json,"expires_in");

        assertThat(expiresIn, is(3600));
    }
}

