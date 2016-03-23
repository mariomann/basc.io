package basc.boundary;

import basc.entity.Account;
import basc.entity.Transaction;
import me.figo.FigoException;
import me.figo.FigoSession;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadTransactionsService {

    public List<Transaction> getLatestTransactions(int numberOfTransactions, Account account) {
        try {
            FigoSession session = new FigoSession("ASHWLIkouP2O6_bgA2wWReRhletgWKHYjLqDaqb0LFfamim9RjexTo22ujRIP_cjLiRiSyQXyt2kM1eXU2XLFZQ0Hro15HikJQT_eNeT_9XQ");
            String figoId = figoIdFor(account);

            List<me.figo.models.Transaction> figoTransactions = session.getTransactions(figoId, null, 3, null, null);

            System.out.println(figoTransactions);

            return figoTransactions
                    .stream()
                    .map(this::fromFigo)
                    .collect(Collectors.toList());

        } catch (FigoException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Transaction fromFigo(me.figo.models.Transaction ft) {
        System.out.println("FigoTransaction");
        System.out.println(ft.getBookingDate());
        System.out.println(ft.getBookingText());
        System.out.println(ft.getPurposeText());

        return new Transaction();
    }

    private String figoIdFor(Account account) {
        return "A1.1";
    }
}
