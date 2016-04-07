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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class ReadTransactionsServiceTest {

    @Autowired
    ReadTransactionsService readTransactionsService;

    @Test
    public void readLatestThreeTransactionsOfAccountWertpapierdepot() {
        Account wertpapierdepot = new Account();

        List<Transaction> transactions = readTransactionsService.getLatestTransactions(3, wertpapierdepot);

        assertThat(transactions.size(), greaterThan(0));
        assertThat(transactions.size(), is(equalTo(3)));
    }
}
