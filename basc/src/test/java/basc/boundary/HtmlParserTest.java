package basc.boundary;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class HtmlParserTest {

    HtmlParser htmlParser = new HtmlParser();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseId() {
        String expected = "LtQvU-RIq7_OJaTRN0pMfAihcxnbHpM5EPkZbh6ctVZw";
        String actual = htmlParser.getResponseId(getHtml("auth-step-1-response.html"));

        assertEquals(expected, actual);
    }

    @Test
    public void parseStep() {
        String expected = "2";
        String actual = htmlParser.getResponseStep(getHtml("auth-step-1-response.html"));

        assertEquals(expected, actual);
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

    private String getHtml(String fileName) {
        try {
            return FileUtils.readFileToString(new File(getClass().getResource(fileName).getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
