package basc.boundary;

import basc.App;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;

import static it.ozimov.cirneco.hamcrest.java7.base.IsBetween.between;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class HttpClientTest {

    @Autowired
    HttpClient httpClient;

    @Test
    public void requestGetResponse() throws IOException {
        String expected = FileUtils.readFileToString(new File(getClass().getResource("example.com-index.html").getPath()));
        HttpClient.Response<String> actual = httpClient.get("http://www.example.com/");

        assertThat(actual.getStatusCode(), is(200));
        assertThat(actual.getBody(), is(equalTo(expected)));
    }

    @Test
    public void requestPostResponse() throws IOException {
        String expected = FileUtils.readFileToString(new File(getClass().getResource("example.com-index.html").getPath()));
        HttpClient.Response<String> actual = httpClient.post("http://www.example.com/");

        assertThat(actual.getStatusCode(), is(200));
        assertThat(actual.getBody(), is(equalTo(expected)));
    }

    @Test
    public void requestGetResponseWithClientFailureStatusCode() {
        HttpClient.Response<String> actual = httpClient.get("http://www.example.com/doesnotexist");

        assertThat(actual.getStatusCode(), is(between(399, 500)));
    }

    @Test
    public void requestPostResponseWithClientFailureStatusCode() {
        HttpClient.Response<String> actual = httpClient.post("http://www.example.com/doesnotexist");

        assertThat(actual.getStatusCode(), is(between(399, 500)));
    }
}
