package basc.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConfigTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getPropertyFromConfigFile() throws IOException {
        String expectedClientId = "CWbLPBQGjG7yqZ5Mg0XdMOHV1Odig0Z-4AteZrUalHqI";

        Properties config = Config.getClientConfig(getClass().getResource("BascClient.config").getPath());
        String actualClientId = config.getProperty("client_id");

        assertThat(actualClientId, is(equalTo(expectedClientId)));
    }

    @Test
    public void getPropertyThrowWhenNotExist() throws IOException {
        thrown.expect(FileNotFoundException.class);
        thrown.expectMessage("does/not/exist/BascClient.cfg (No such file or directory)");

        Config.getClientConfig("does/not/exist/BascClient.cfg");
    }
}
