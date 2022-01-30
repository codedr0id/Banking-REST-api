package paytm.assignment.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paytm.assignment.Models.User;
import paytm.assignment.Models.Wallet;
import paytm.assignment.Repositories.WalletRepository;

import java.util.List;

@Service
public class WalletService {

    @Autowired
    private WalletRepository repo;

    @Autowired
    private UserService service;

    public List<Wallet> getAllWallets() {
        return repo.findAll();
    }

    public String createWallet(String mobileNo) {
        User user = service.getByPhoneNo(mobileNo);

        if (user == null){
            return "User not found";
        }

        if (!user.isWalletActive()) {
            user.setWalletActive(true);
            Wallet wallet = new Wallet(mobileNo, 0);
            repo.save(wallet);
            return "Wallet created";
        }

        return "Wallet exists already";

    }

    public String deposit(String mobileNo, Double amount) {
        User user = service.getByPhoneNo(mobileNo);

        if (user == null){
            return "User not found";
        }

        if (!user.isWalletActive()) {
            return "Create wallet first";
        } else if (amount <= 0) {
            return "Amount should be greater than 0";
        }

        Wallet wallet = repo.findByMobileNo(mobileNo);
        wallet.setBalance(wallet.getBalance() + amount);
        repo.save(wallet);
        return "Money added";
    }

    public String transfer(String payerNo, String payeeNo, Double amount) {
        User payer = service.getByPhoneNo(payerNo);
        User payee = service.getByPhoneNo(payeeNo);

        if (payer == null){
            return "Payer not found";
        }

        if (payee == null){
            return "Payee not found";
        }

        if (!payer.isWalletActive()) {
            return "Create Payer's wallet first";
        } else if (!payee.isWalletActive()) {
            return "Create Payee's wallet first";
        } else if (amount <= 0) {
            return "Amount should be greater than 0";
        }

        Wallet payerWallet = repo.findByMobileNo(payerNo);
        Wallet payeeWallet = repo.findByMobileNo(payeeNo);

        if (payerWallet.getBalance() < amount) {
            return "Insufficient balance";
        }

        payerWallet.setBalance(payerWallet.getBalance() - amount);
        payeeWallet.setBalance(payeeWallet.getBalance() + amount);
        repo.save(payerWallet);
        repo.save(payeeWallet);
        return "Money transferred";
    }
}
