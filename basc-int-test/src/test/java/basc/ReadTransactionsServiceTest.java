package basc;

import basc.boundary.ReadTransactionsService;
import basc.entity.Account;
import basc.entity.Transaction;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class ReadTransactionsServiceTest {

    @Autowired
    ReadTransactionsService readTransactionsService;

    @Test
    @Ignore
    public void readLatestThreeTransactionsOfAccountWertpapierdepot() {
        Account wertpapierdepot = new Account("Wertpapierdepot");

        List<Transaction> transactions = readTransactionsService.getLatestTransactions(3, wertpapierdepot);

        assertEquals(3, transactions.size());
    }
}
