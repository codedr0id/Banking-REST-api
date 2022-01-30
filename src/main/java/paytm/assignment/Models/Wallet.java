package paytm.assignment.Models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Wallet {
    @Id
    private String mobileNo;
    private double balance;

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
}
