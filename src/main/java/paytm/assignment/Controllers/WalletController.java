package paytm.assignment.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import paytm.assignment.DTO.ResponseObject;
import paytm.assignment.DTO.TransferDetails;
import paytm.assignment.Exceptions.AmountGreaterThanZero;
import paytm.assignment.Exceptions.UserNotFound;
import paytm.assignment.Exceptions.WalletNotFound;
import paytm.assignment.Models.Wallet;
import paytm.assignment.Services.WalletService;

import java.util.List;

@RestController
public class WalletController {

    @Autowired
    WalletService walletService;

    @GetMapping("/wallets")
    public List<Wallet> getWallets() {
        return walletService.getAllWallets();
    }

    @PostMapping("/create-wallet/{mobileNo}")
    public ResponseEntity<ResponseObject> createWallet(@PathVariable("mobileNo") String mobileNo) {
        try {walletService.createWallet(mobileNo);
            return new ResponseEntity<>(new ResponseObject(HttpStatus.CREATED.value(), "Wallet created", walletService.getWallet(mobileNo)), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(new ResponseObject(HttpStatus.FORBIDDEN.value(), e.getLocalizedMessage(), null), HttpStatus.FORBIDDEN);

        }
    }

    @PostMapping("/deposit/{mobileNo}/{amount}")
    public ResponseEntity<ResponseObject> deposit(@PathVariable("mobileNo") String mobileNo, @PathVariable("amount") int amount) throws UserNotFound, AmountGreaterThanZero, WalletNotFound {
        try {
            walletService.deposit(mobileNo, (double) amount);
            return new ResponseEntity<>(new ResponseObject(HttpStatus.OK.value(), "Money added", walletService.getWallet(mobileNo)), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(new ResponseObject(HttpStatus.FORBIDDEN.value(), e.getLocalizedMessage(), null), HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping("/transfer/{payerMobileNo}/{payeeMobileNo}/{amount}")
    public ResponseEntity<ResponseObject> transfer(@PathVariable("payerMobileNo") String payerMobileNo, @PathVariable("payeeMobileNo") String payeeMobileNo, @PathVariable("amount") Double amount) {
        try {
            walletService.transfer(payerMobileNo, payeeMobileNo, (Double) amount);
            return new ResponseEntity<>(new ResponseObject(HttpStatus.OK.value(), "Transfer successful", new TransferDetails(payerMobileNo, payeeMobileNo, amount)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseObject(HttpStatus.FORBIDDEN.value(), e.getLocalizedMessage(), new TransferDetails(payerMobileNo, payeeMobileNo, amount)), HttpStatus.FORBIDDEN);
        }
    }
}
