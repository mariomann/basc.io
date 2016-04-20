package basc.controller;

import basc.boundary.HtmlParser;
import basc.boundary.HttpClient;
import basc.boundary.JsonParser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.httpclient.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AuthController {

    @Autowired
    HttpClient httpClient;

    @Autowired
    JsonParser jsonParser;

    @Autowired
    HtmlParser htmlParser;

    private static final String AUTH_BASE_URL = "https://api.figo.me/auth";

    public AuthenticationToken receiveAccessToken(String clientId, String clientSecret, String state, String username, String password) {
        Request r = new Request(clientId, clientSecret, state, username, password);

        return asFunction(this::requestAndParsePage1)
                .andThen(this::requestAndParsePage2)
                .andThen(this::requestAndParsePage3)
                .andThen(this::requestAndParsePage4)
                .apply(r);
    }

    @RequiredArgsConstructor
    private class Request {
        final String clientId;
        final String clientSecret;
        final String state;
        final String username;
        final String password;
    }

    @RequiredArgsConstructor
    private class Page1Response {
        final Request request;
        final String responseId;
        final String step;
    }

    @RequiredArgsConstructor
    private class Page2Response {
        final Request request;
        final String responseId;
        final List<String> accounts;
        final String step;
    }

    @RequiredArgsConstructor
    private class Page3Response {
        final Request request;
        final String code;
    }

    private Page1Response requestAndParsePage1(Request r) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("client_id", r.clientId);
        parameter.put("response_type", "code");
        parameter.put("state", r.state);
        HttpClient.Response<String> pageOne = httpClient.get(AUTH_BASE_URL + "/code", parameter);


        String responseId = htmlParser.getResponseId(pageOne.getBody());
        String nextStep = htmlParser.getResponseStep(pageOne.getBody());

        return new Page1Response(
            r,
            responseId,
            nextStep
        );
    }

    private Page2Response requestAndParsePage2(Page1Response p1v) {
        Map<String, String> params = new HashMap<>();
        params.put("username", p1v.request.username);
        params.put("password", p1v.request.password);
        params.put("id", p1v.responseId);
        params.put("step", p1v.step);
        HttpClient.Response<String> pageTwo = httpClient.post(AUTH_BASE_URL + "/login", params);

        List<String> accounts = htmlParser.getResponseAccounts(pageTwo.getBody());
        String nextStep = htmlParser.getResponseStep(pageTwo.getBody());

        return new Page2Response(
            p1v.request,
            p1v.responseId,
            accounts,
            nextStep
        );
    }

    private Page3Response requestAndParsePage3(Page2Response p2v) {
        Map<String, String> par = p2v.accounts.stream().collect(Collectors.toMap(e -> e, e -> "1"));
        par.put("id", p2v.responseId);
        par.put("step", p2v.step);

        HttpClient.Response<String> pageThree = httpClient.post(AUTH_BASE_URL + "/login", par);

        String redirect = Arrays.stream(pageThree.getHeaders())
                .filter(h -> h.getName()
                        .contains("Location"))
                .findFirst()
                .get()
                .getValue();

        String[] paramPairs = redirect.split("\\?")[1].split("&");
        Map<String, String> map = Arrays.stream(paramPairs)
                .map(s -> s.split("="))
                .collect(Collectors.toMap(m -> m[0], m -> m[1]));

        return new Page3Response(
            p2v.request,
            map.get("code")
        );
    }

    private AuthenticationToken requestAndParsePage4(Page3Response p3v) {
        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "authorization_code");
        body.put("code", p3v.code);

        String credentials = String.format("%s:%s", p3v.request.clientId, p3v.request.clientSecret);
        Header[] header = {new Header("Authorization", "Basic " + new String(org.apache.commons.codec.binary.Base64.encodeBase64(credentials.getBytes())))};

        HttpClient.Response<String> pageFour = httpClient.post(AUTH_BASE_URL + "/token", body, header);

        return new AuthenticationToken(jsonParser.parseString(pageFour.getBody(), "access_token"), LocalDateTime.now().plusSeconds(jsonParser.parseInteger(pageFour.getBody(), "expires_in")));
    }

    private<T, U> Function<T, U> asFunction(Function<T, U> f) { return f; }
}
