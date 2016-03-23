package basc.boundary;

import basc.entity.*;
import basc.entity.Account;
import basc.entity.Transaction;
import me.figo.FigoException;
import me.figo.FigoSession;
import me.figo.models.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ReadTransactionsService {

    public List<Transaction> getLatestTransactions(int numberOfTransactions, Account account) {
        try {
        FigoSession session = new FigoSession("ASHWLIkouP2O6_bgA2wWReRhletgWKHYjLqDaqb0LFfamim9RjexTo22ujRIP_cjLiRiSyQXyt2kM1eXU2XLFZQ0Hro15HikJQT_eNeT_9XQ");
        String figoId = figoIdFor(account);

        me.figo.models.Account figoAccount = session.getAccount(figoId);
        //ToDo session.getTransactions(3, figoId);
        session.getTransactions();

        return null;
        } catch (FigoException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String figoIdFor(Account account) {
        return "A1.4";
    }
}
