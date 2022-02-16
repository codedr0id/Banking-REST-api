package paytm.assignment.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paytm.assignment.Models.Transaction;
import paytm.assignment.Repositories.TransactionRepository;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository repo;

    public List<Transaction> getAllTransactions(String payer) {
        return repo.findAllByFromMobileNo(payer);
    }

    public Transaction getTransaction(String id) {
        return repo.findById(id).get();
    }

    public void saveTransaction(Transaction transaction) {
        repo.save(transaction);
    }
}
