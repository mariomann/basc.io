package basc.controller;

import basc.App;
import basc.util.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class AuthControllerTest {

    @Autowired
    AuthController auth;

    @Test
    public void receiveValidAccessToken() throws IOException {
        Properties config = Config.getClientConfig(getClass().getResource("BascClient.config").getPath());

        AuthenticationToken token = auth.receiveAccessToken(config.getProperty("client_id"), config.getProperty("client_secret"), "code", "ABC", config.getProperty("username"), config.getProperty("password"));

        assertThat(token.isValidAt(LocalDateTime.now()), is(true));
    }
}
