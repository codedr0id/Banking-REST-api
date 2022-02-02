package paytm.assignment.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction {
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    @Id
    private String id;
    private String fromMobileNo;
    private String toMobileNo;
    private Double amount;
    private String action;
    private Date timestamp;

    public Transaction() {
    }

    public Transaction(String fromMobileNo, String toMobileNo, Double amount, String action, Date timestamp) {
        this.fromMobileNo = fromMobileNo;
        this.toMobileNo = toMobileNo;
        this.amount = amount;
        this.action = action;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromMobileNo() {
        return fromMobileNo;
    }

    public void setFromMobileNo(String fromMobileNo) {
        this.fromMobileNo = fromMobileNo;
    }

    public String getToMobileNo() {
        return toMobileNo;
    }

    public void setToMobileNo(String toMobileNo) {
        this.toMobileNo = toMobileNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
