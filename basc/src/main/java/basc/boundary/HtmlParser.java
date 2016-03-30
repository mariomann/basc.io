package basc.boundary;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HtmlParser {

    public String getResponseId(String html) {
        return getResponseField(html, "id");
    }

    public String getResponseStep(String html) {
        return getResponseField(html, "step");
    }

    private String getResponseField(String html, String field) {
        Document doc = Jsoup.parse(html);

        Elements maybeStep = findFields(doc, field);

        if(maybeStep.size() != 1) {
            throw new IllegalStateException("Did not find exactly 1 " + field + " field, but " + maybeStep.size());
        }

        return maybeStep.get(0).val();
    }

    private Elements findFields(Document doc, String fieldName) {
        String parentElement = "div.content-pane";
        String selector = String.format("%s input[name=\"%s\"]", parentElement, fieldName);
        return doc.select(selector);
    }

}
