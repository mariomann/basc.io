package basc.boundary;


import lombok.Data;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class HttpClient {

    @Autowired
    org.apache.commons.httpclient.HttpClient httpClient;

    public Response<String> get(String url) {
        return get(url, Collections.emptyMap());
    }

    public Response<String> get(String url, Map<String, String> query) {
        String parameter = query.entrySet().stream()
            .map(e -> e.getKey() + "=" + e.getValue())
            .collect(Collectors.joining("&"));
        GetMethod getMethod = new GetMethod(url + "?" + parameter);

        return execute(getMethod);
    }

    public Response<String> post(String url) {
        return post(url, Collections.emptyMap());
    }

    public Response<String> post(String url, Map<String, String> body) {
        PostMethod postMethod = new PostMethod(url);

        NameValuePair[] pairs = body.entrySet().stream()
            .map(e -> new NameValuePair(e.getKey(), e.getValue()))
            .toArray(NameValuePair[]::new);
        postMethod.setRequestBody(pairs);

        return execute(postMethod);
    }

    private Response<String> execute(HttpMethod method) {
        try {
            int status = httpClient.executeMethod(method);
            String result = method.getResponseBodyAsString();

            return new Response<>(status, result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    public static class Response<T> {
        public final int statusCode;
        public final T body;
    }
}
