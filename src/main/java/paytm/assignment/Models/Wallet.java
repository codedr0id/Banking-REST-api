package paytm.assignment.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Wallet {
    @Id
    @Column(name = "mobile_no")
    private String mobileNo;
    private double balance;

//    @OneToMany(targetEntity = Transaction.class, cascade = CascadeType.ALL)
//    private List<Transaction> transactions;

    public Wallet() {
    }

    public Wallet(String mobileNo, double balance) {
        this.mobileNo = mobileNo;
        this.balance = balance;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

//    public List<Transaction> getTransactions() {
//        return transactions;
//    }
//
//    public void addTransaction(Transaction transaction) {
//        this.transactions.add(transaction);
//    }
}
