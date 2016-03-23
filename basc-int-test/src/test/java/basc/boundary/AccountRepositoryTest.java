package basc.boundary;

import basc.App;
import basc.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void createAndReadOneAccount() {
        Account a = new Account();
        a.setFigoId("42");
        assertNull(a.getId());

        accountRepository.save(a);

        Account aRead = accountRepository.findByFigoId(a.getFigoId());
        assertEquals(a, aRead);
        assertTrue(aRead.getId() > 0);
    }

}
