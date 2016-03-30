package basc.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue
    Integer id;

    String figoId;

    @OneToMany(fetch = FetchType.EAGER)
    List<Transaction> transactions = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (id != null ? !id.equals(account.id) : account.id != null) return false;
        if (figoId != null ? !figoId.equals(account.figoId) : account.figoId != null) return false;
        return transactions != null ? this.getTransactions().containsAll(account.getTransactions()) == account.getTransactions().containsAll(this.getTransactions()) : account.transactions == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (figoId != null ? figoId.hashCode() : 0);
        result = 31 * result + (transactions != null ? transactions.hashCode() : 0);
        return result;
    }
}
