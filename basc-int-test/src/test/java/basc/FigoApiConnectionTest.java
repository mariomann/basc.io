package basc;

import me.figo.FigoException;
import me.figo.FigoSession;
import me.figo.models.Account;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class FigoApiConnectionTest {

    @Test
    public void readAccountNames() throws FigoException, IOException {
        FigoSession session = new FigoSession("ASHWLIkouP2O6_bgA2wWReRhletgWKHYjLqDaqb0LFfamim9RjexTo22ujRIP_cjLiRiSyQXyt2kM1eXU2XLFZQ0Hro15HikJQT_eNeT_9XQ");

        Set<String> accountNames = session.getAccounts()
                .stream()
                .map(Account::getName)
                .collect(Collectors.toCollection(HashSet::new));

        Set<String> expectedNames = new HashSet<>(Arrays.asList("Girokonto", "Sparkonto", "Wertpapierdepot"));

        assertEquals(expectedNames, accountNames);
    }

}
