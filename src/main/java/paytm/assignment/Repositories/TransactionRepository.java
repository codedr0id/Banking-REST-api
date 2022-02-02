package paytm.assignment.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import paytm.assignment.Models.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findAllByFromMobileNo(String fromMobileNo);
}
