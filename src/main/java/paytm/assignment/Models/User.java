package paytm.assignment.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    //primary key/id
    @Id
    private String username;

    private String firstname;
    private String lastname;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String mobile;

    private String address1;
    private String address2;

    private boolean isWalletActive;

    public User() {
    }

    //Constructor
    public User(String username, String firstname, String lastname, String email, String mobile, String address1, String address2, boolean isWalletActive) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.mobile = mobile;
        this.address1 = address1;
        this.address2 = address2;
        this.isWalletActive = false;
    }

    //Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public boolean isWalletActive() {
        return isWalletActive;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", isWalletActive=" + isWalletActive +
                '}';
    }

    public void setWalletActive(boolean walletActive) {
        isWalletActive = walletActive;
    }

    //checking if the requirements are met or not
    public String check(){
        if (username == null || username == "") return "username";
        if (firstname == null || firstname == "") return "firstname";
        if (lastname == null || lastname == "") return "lastname";
        if (mobile == null || mobile == "") return "mobile";
        if (email == null || email == "") return "email";
        if (address1 == null || address1 =="") return "address";

        return "ok";
    }
}
