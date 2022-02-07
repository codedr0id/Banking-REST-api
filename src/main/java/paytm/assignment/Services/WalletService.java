package paytm.assignment.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paytm.assignment.Exceptions.*;
import paytm.assignment.Models.Transaction;
import paytm.assignment.Models.User;
import paytm.assignment.Models.Wallet;
import paytm.assignment.Repositories.WalletRepository;

import java.util.Date;
import java.util.List;

@Service
public class WalletService {

    @Autowired
    private WalletRepository repo;

    @Autowired
    private UserService service;

    @Autowired
    private TransactionService transactionService;

    public List<Wallet> getAllWallets() {
        return repo.findAll();
    }

    public Wallet getWallet(String mobileNo) {
        return repo.findByMobileNo(mobileNo);
    }

    public void deleteWallet(String mobileNo) {
        repo.deleteById(mobileNo);
    }

    public Wallet createWallet(String mobileNo) throws WalletAlreadyExists, UserNotFound {
        User user = service.getByPhoneNo(mobileNo);

        if (user == null) {
            throw new UserNotFound("");
        }

        if (user.isWalletActive()) {
            throw new WalletAlreadyExists();
        }

        user.setWalletActive(true);
        Wallet wallet = new Wallet();
        wallet.setMobileNo(mobileNo);
        wallet.setBalance(0.0);
        repo.save(wallet);
        return wallet;

    }

    public Wallet deposit(String mobileNo, Double amount) throws WalletNotFound, UserNotFound, AmountGreaterThanZero {
        User user = service.getByPhoneNo(mobileNo);

        if (user == null) {
            throw new UserNotFound("");
        }

        if (!user.isWalletActive()) {
            throw new WalletNotFound("");}

        if (amount <= 0) {
            throw new AmountGreaterThanZero();
        }

        Wallet wallet = repo.findByMobileNo(mobileNo);
        wallet.setBalance(wallet.getBalance() + amount);
        repo.save(wallet);
        Transaction transaction = new Transaction(mobileNo, "self", amount, "Deposit", new Date());
        transactionService.saveTransaction(transaction);
        return wallet;
    }

    public Transaction transfer(String payerNo, String payeeNo, Double amount) throws UserNotFound, WalletNotFound, AmountGreaterThanZero, InsufficientBalance {
        User payer = service.getByPhoneNo(payerNo);
        User payee = service.getByPhoneNo(payeeNo);

        if (payer == null) {
            throw new UserNotFound("Payer");
        }

        if (payee == null) {
            throw new UserNotFound("Payee");
        }

//        if (!payer.isWalletActive()) {
//            throw new WalletNotFound(("Payer's"));
//        } else if (!payee.isWalletActive()) {
//            throw new WalletNotFound(("Payee's"));}
        if (amount <= 0) {
            throw new AmountGreaterThanZero();
        }

        Wallet payerWallet = repo.findByMobileNo(payerNo);
        Wallet payeeWallet = repo.findByMobileNo(payeeNo);

        if (payerWallet.getBalance() < amount) {
            throw new InsufficientBalance();
        }

        Transaction transaction = new Transaction(payerNo, payeeNo, amount, "Transfer", new Date());
        transactionService.saveTransaction(transaction);
//        payerWallet.addTransaction(transactionService.getTransaction(transaction.getId()));
//        payerWallet.addTransaction(transactionService.getTransaction(transaction.getId()));
        payerWallet.setBalance(payerWallet.getBalance() - amount);
        payeeWallet.setBalance(payeeWallet.getBalance() + amount);
        repo.save(payerWallet);
        repo.save(payeeWallet);
        return transaction;
    }
}
