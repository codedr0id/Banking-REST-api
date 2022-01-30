package paytm.assignment.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import paytm.assignment.Models.Wallet;
import paytm.assignment.Services.WalletService;

import java.util.List;
import java.util.Objects;

@RestController
public class WalletController {

    @Autowired
    WalletService walletService;

    @GetMapping("/wallets")
    public List<Wallet> getWallets() {
        return walletService.getAllWallets();
    }

    @PostMapping("/create-wallet/{mobileNo}")
    public String createWallet(@PathVariable("mobileNo") String mobileNo) {
        if (Objects.equals(walletService.createWallet(mobileNo), "Wallet created")) {
            return "Wallet created successfully";
        } else if (Objects.equals(walletService.createWallet(mobileNo), "User not found")) {
            return "Create user first";
        }
        return "Wallet already exists";
    }

    @PostMapping("/deposit/{mobileNo}/{amount}")
    public String deposit(@PathVariable("mobileNo") String mobileNo, @PathVariable("amount") int amount) {
        if (Objects.equals(walletService.deposit(mobileNo, (double) amount), "Money added")) {
            return "Deposit successful";
        }
        return "Deposit failed";
    }

    @PostMapping("/transfer/{payerMobileNo}/{payeeMobileNo}/{amount}")
    public String transfer(@PathVariable("payerMobileNo") String payerMobileNo, @PathVariable("payeeMobileNo") String payeeMobileNo, @PathVariable("amount") int amount) {
        if (Objects.equals(walletService.transfer(payerMobileNo, payeeMobileNo, (double) amount), "Money transferred")) {
            return "Transfer successful";
        }
        else if (Objects.equals(walletService.transfer(payerMobileNo, payeeMobileNo, (double) amount), "Insufficient balance")) {
            return "Insufficient funds in your account";
        }
        return "Transfer failed";
    }
}
