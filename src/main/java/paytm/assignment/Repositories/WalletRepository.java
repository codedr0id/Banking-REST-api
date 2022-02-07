package paytm.assignment.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import paytm.assignment.Models.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, String> {
    Wallet findByMobileNo(String mobileNo);
}
