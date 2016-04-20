package basc.boundary;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HtmlParser {

    public String getResponseId(String html) {
        return getResponseField(html, "div.content-pane", "id");
    }

    public String getResponseStep(String html) { return getResponseField(html, "div.content-pane", "step"); }

    public List<String> getResponseAccounts(String html) { return getResponseAccounts(html, "#accounts", "input"); }

    private String getResponseField(String html, String parentElement, String field) {
        Document doc = Jsoup.parse(html);

        Elements maybeStep = findInputFields(doc, parentElement, field);

        if(maybeStep.size() != 1) {
            throw new IllegalStateException("Did not find exactly 1 " + field + " field, but " + maybeStep.size());
        }

        return maybeStep.get(0).val();
    }

    private List<String> getResponseAccounts(String html, String parentElement, String field) {
        Document doc = Jsoup.parse(html);
        List<String> accounts = new ArrayList<>();

        Elements maybeStep = findAccounts(doc, parentElement, field);

        if(maybeStep.isEmpty()) {
            throw new IllegalStateException("Did not find any account!");
        }

        List<Element> elements = maybeStep.subList(0, maybeStep.size());

        accounts.addAll(
            elements.stream()
                .map(Element::id)
                .collect(Collectors.toList()));

        return accounts;
    }

    private Elements findAccounts(Document doc, String parentElement, String fieldName) {
        String selector = String.format("%s %s", parentElement, fieldName);
        return doc.select(selector);
    }

    private Elements findInputFields(Document doc, String parentElement, String fieldName) {
        String selector = String.format("%s input[name=\"%s\"]", parentElement, fieldName);
        return doc.select(selector);
    }

}
