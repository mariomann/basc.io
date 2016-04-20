package basc.controller;

import basc.boundary.HtmlParser;
import basc.boundary.HttpClient;
import basc.boundary.JsonParser;
import org.apache.commons.httpclient.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AuthController {

    @Autowired
    HttpClient httpClient;

    JsonParser jsonParser = new JsonParser();
    HtmlParser htmlParser = new HtmlParser();

    private static final String AUTH_BASE_URL = "https://api.figo.me/auth";

    public AuthenticationToken receiveAccessToken(String clientId, String clientSecret, String responseType, String state, String username, String password) {

        Map<String, String> parameter = new HashMap<>();
        parameter.put("client_id", clientId);
        parameter.put("response_type", responseType);
        parameter.put("state", state);
        HttpClient.Response<String> pageOne = httpClient.get(AUTH_BASE_URL + "/code", parameter);


        String responseId = htmlParser.getResponseId(pageOne.getBody());
        String nextStep = htmlParser.getResponseStep(pageOne.getBody());

        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("id", responseId);
        params.put("step", nextStep);
        HttpClient.Response<String> pageTwo = httpClient.post(AUTH_BASE_URL + "/login", params);

        List<String> accounts = htmlParser.getResponseAccounts(pageTwo.getBody());
        nextStep = htmlParser.getResponseStep(pageTwo.getBody());

        Map<String, String> par = accounts.stream().collect(Collectors.toMap(e -> e, e -> "1"));
        par.put("id", responseId);
        par.put("step", nextStep);

        HttpClient.Response<String> pageThree = httpClient.post(AUTH_BASE_URL + "/login", par);

        String redirect = Arrays.stream(pageThree.getHeaders())
                                    .filter(h -> h.getName()
                                    .contains("Location"))
                                    .findFirst()
                                    .get()
                                    .getValue();

        Map<String, String> map = Arrays.stream(redirect.split("&"))
                                    .map(s -> s.split("="))
                                    .collect(Collectors.toMap(m -> m[0], m -> m[1]));

        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "authorization_code");
        body.put("code", map.get("code"));

        String credentials = clientId + ":" + clientSecret;
        Header[] header = {new Header("Authorization", "Basic " + new String(org.apache.commons.codec.binary.Base64.encodeBase64(credentials.getBytes())))};

        HttpClient.Response<String> pageFour = httpClient.post(AUTH_BASE_URL + "/token", body, header);

        System.out.println(pageFour.getBody());

        return new AuthenticationToken(jsonParser.parseString(pageFour.getBody(), "access_token"), LocalDateTime.now().plusSeconds(jsonParser.parseInteger(pageFour.getBody(), "expires_in")));
    }
}
