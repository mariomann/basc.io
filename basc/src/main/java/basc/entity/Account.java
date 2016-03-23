package basc.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue
    Integer id;

    String figoId;

    @OneToMany(fetch = FetchType.EAGER)
    List<Transaction> transactions = new ArrayList<>();

}
