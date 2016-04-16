package basc.controller;

import basc.boundary.HtmlParser;
import basc.boundary.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AuthController {

    @Autowired
    HttpClient httpClient;

    public AuthenticationToken receiveAccessToken(String clientId, String responseType, String state, String username, String password) {

        Map<String, String> parameter = new HashMap<>();
        parameter.put("client_id", clientId);
        parameter.put("response_type", responseType);
        parameter.put("state", state);
        HttpClient.Response<String> pageOne = httpClient.get("https://api.figo.me/auth/code", parameter);

        HtmlParser parser = new HtmlParser();
        String responseId = parser.getResponseId(pageOne.getBody());
        String nextStep = parser.getResponseStep(pageOne.getBody());

        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("id", responseId);
        params.put("step", nextStep);
        HttpClient.Response<String> pageTwo = httpClient.post("https://api.figo.me/auth/login", Collections.emptyMap());

        List<String> accounts = parser.getResponseAccounts(pageTwo.getBody());

        Map<String, String> par = accounts.stream().collect(Collectors.toMap(e -> e, e -> "1"));
        par.put("id", responseId);
        par.put("step", nextStep + 1);
        HttpClient.Response<String> pageThree = httpClient.post("https://api.figo.me/auth/login", par);

        System.out.println(pageThree.getBody());

        return new AuthenticationToken("ASHWLIkouP2O6_bgA2wWReRhletgWKHYjLqDaqb0LFfamim9RjexTo22ujRIP_cjLiRiSyQXyt2kM1eXU2XLFZQ0Hro15HikJQT_eNeT_9XQ", LocalDateTime.now().plusSeconds(3600));
    }
}
