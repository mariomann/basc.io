package basc.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue
    Integer id;
}
