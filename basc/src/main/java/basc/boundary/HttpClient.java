package basc.boundary;


import lombok.Data;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpClient {

    @Autowired
    org.apache.commons.httpclient.HttpClient httpClient;

    public Response<String> get(String url) {
        GetMethod getMethod = new GetMethod(url);

        return execute(getMethod);
    }

    public Response<String> post(String url) {
        PostMethod postMethod = new PostMethod(url);
        NameValuePair[] data = {};
        postMethod.setRequestBody(data);

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
