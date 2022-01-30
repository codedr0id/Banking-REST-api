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

    public List<Wallet> getAllWallets(){
        return repo.findAll();
    }

    public String createWallet(String mobileNo) {
        User user = service.getByPhoneNo(mobileNo);

        if (!user.isWalletActive()) {
            user.setWalletActive(true);
            Wallet wallet = new Wallet(mobileNo, 0);
            repo.save(wallet);
            return "Wallet created";
        }

        return "Wallet exists already";

    }

    public String deposit(Wallet wallet) {
        User user = service.getByPhoneNo(wallet.getMobileNo());

        if (!user.isWalletActive()) {
            return "Create wallet first";
        }

        repo.save(wallet);
        return "Money added";

    }
}
