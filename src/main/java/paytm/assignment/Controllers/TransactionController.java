package paytm.assignment.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import paytm.assignment.Models.Transaction;
import paytm.assignment.Services.TransactionService;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/transactions/{payerMobileNo}")
    public List<Transaction> getTransaction(@PathVariable("payerMobileNo") String payerMobileNo) {
        return transactionService.getAllTransactions(payerMobileNo);
    }
}
