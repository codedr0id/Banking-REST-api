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
        }
        return "Wallet already exists";
    }
}
