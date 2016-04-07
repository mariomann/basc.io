package basc.boundary;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HtmlParserTest {

    HtmlParser htmlParser = new HtmlParser();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseId() {
        String expected = "LtQvU-RIq7_OJaTRN0pMfAihcxnbHpM5EPkZbh6ctVZw";
        String actual = htmlParser.getResponseId(getHtml("auth-step-1-response.html"));

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void parseStep() {
        String expected = "2";
        String actual = htmlParser.getResponseStep(getHtml("auth-step-1-response.html"));

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void parseIdThrowsWhenNoIdFieldPresent() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Did not find exactly 1 id field, but 0");

        htmlParser.getResponseId(getHtml("auth-step-1-response-no-id-field.html"));
    }

    @Test
    public void parseIdThrowsWhenMoreIdFieldsPresent()  {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Did not find exactly 1 id field, but 2");

        htmlParser.getResponseId(getHtml("auth-step-1-response-two-id-fields.html"));
    }

    @Test
    public void parseAccounts() {
        List<String> actual = htmlParser.getResponseAccounts(getHtml("auth-step-2-response.html"));

        assertThat(actual.size(), is(3));

        assertThat(actual.get(0), is(equalTo("A1238764.1")));
        assertThat(actual.get(1), is(equalTo("A1238764.2")));
        assertThat(actual.get(2), is(equalTo("A1238764.3")));
    }

    @Test
    public void parseAccountsThrowsWhenNoAccountPresent() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Did not find any account!");

        htmlParser.getResponseAccounts(getHtml("auth-step-2-response-no-accounts.html"));
    }

    private String getHtml(String fileName) {
        try {
            return FileUtils.readFileToString(new File(getClass().getResource(fileName).getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
