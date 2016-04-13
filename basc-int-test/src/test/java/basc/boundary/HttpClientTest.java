package basc.boundary;

import basc.App;
import by.stub.server.StubbyManager;
import by.stub.server.StubbyManagerFactory;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static it.ozimov.cirneco.hamcrest.java7.base.IsBetween.between;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class HttpClientTest {

    @Autowired
    HttpClient httpClient;

    StubbyManagerFactory stubbyManagerFactory = new StubbyManagerFactory();
    StubbyManager stubbyManager;

    @Test
    public void getOnLocalhostReturns200() throws IOException {
        HttpClient.Response<String> actual = httpClient.get("http://localhost:8882/");

        assertThat(actual.getStatusCode(), is(200));
        assertThat(actual.getBody(), is(equalTo("Hello basc.io")));
    }

    @Test
    public void getWithParameterOnLocalhostReturns200() {
        Map<String, String> query = new HashMap<>();
        query.put("lol", "rofl");
        query.put("hehe", "hihi");
        HttpClient.Response<String> actual = httpClient.get("http://localhost:8882/", query);

        assertThat(actual.getStatusCode(), is(200));
        assertThat(actual.getBody(), is(equalTo("<lol>rofl</lol><hehe>hihi</hehe>")));
    }

    @Test
    public void postOnLocalhostReturns200() throws IOException {
        HttpClient.Response<String> actual = httpClient.post("http://localhost:8882/");

        assertThat(actual.getStatusCode(), is(200));
        assertThat(actual.getBody(), is(equalTo("Hello basc.io")));
    }

    @Test
    public void postWithBodyOnLocalhostReturns200() {
        Map<String, String> body = new HashMap<>();
        body.put("lol", "rofl");
        body.put("hehe", "hihi");
        HttpClient.Response<String> actual = httpClient.post("http://localhost:8882/lol", body);

        assertThat(actual.getStatusCode(), is(200));
        assertThat(actual.getBody(), is(equalTo("<lol>rofl</lol><hehe>hihi</hehe>")));
    }

    @Test
    public void getOnNonExistingSiteReturns404() {
        HttpClient.Response<String> actual = httpClient.get("http://localhost:8882/doesnotexist");

        assertThat(actual.getStatusCode(), is(404));
    }

    @Test
    public void postOnNonExistingSiteReturns404() {
        HttpClient.Response<String> actual = httpClient.post("http://localhost:8882/doesnotexist");

        assertThat(actual.getStatusCode(), is(404));
    }

    @Before
    public void startHttpServer() throws Exception {
        Map<String, String> startParameter = new HashMap<>();
        startParameter.put("location", "localhost");
        startParameter.put("stubs", "8882");
        startParameter.put("disable_admin_portal", "");

        stubbyManager = stubbyManagerFactory.construct(String.valueOf(new File(getClass().getResource("stubbyDataFile.yml").getPath())), startParameter);

        stubbyManager.startJetty();
    }

    @After
    public void stopHttpServer() throws Exception {
        stubbyManager.stopJetty();
    }
}
