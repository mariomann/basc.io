package basc.controller;

import basc.App;
import basc.util.Config;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringApplicationConfiguration(App.class)
public class AuthControllerTest {

    @Test
    public void receiveValidAccessToken() throws IOException {
        Properties config = Config.getClientConfig(getClass().getResource("../util/BascClient.config").getPath());


        AuthController auth = new AuthController();
        AuthenticationToken token = auth.receiveAccessToken(config.getProperty("client_id"), "code", "ABC", config.getProperty("username"), config.getProperty("password"));

        assertThat(token.isValidAt(LocalDateTime.now()), is(true));
    }
}
