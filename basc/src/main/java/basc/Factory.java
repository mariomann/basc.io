package basc;

import org.apache.commons.httpclient.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Factory {

    @Bean
    public HttpClient createHttpClient() {
        return new HttpClient();
    }

}
