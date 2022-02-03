package paytm.assignment.Services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import paytm.assignment.Exceptions.AmountGreaterThanZero;
import paytm.assignment.Exceptions.UserNotFound;
import paytm.assignment.Exceptions.WalletAlreadyExists;
import paytm.assignment.Exceptions.WalletNotFound;

@SpringBootTest
class WalletServiceTest {

    @Autowired
    WalletService walletService;

    @Test
    @DisplayName("Test for wallet creation success")
    void createWalletSuccess() throws UserNotFound, WalletAlreadyExists {
//        User user = new User("godfather", "Mehul", "Lathi", "mehullathi1999@gmail.com", "8875019993", "Udaipur", "Raj", false);
//        Mockito.when(userRepository.save(user)).thenReturn(user).;
//        assertEquals(walletService.createWallet("8875019993"), "Wallet created successfully");
        Assertions.assertThat(walletService.createWallet("8875019993")).isEqualTo("Wallet created successfully");
    }

    @Test
    @DisplayName("Test for wallet creation where user not found")
    void createWalletUserNotFound() {
        Assertions.assertThatThrownBy(() -> walletService.createWallet("8875019992")).isInstanceOf(UserNotFound.class);
    }

    @Test
    @DisplayName("Test for wallet creation where wallet already exists")
    void createWalletAlreadyExists() {
        Assertions.assertThatThrownBy(() -> walletService.createWallet("7014219283")).isInstanceOf(WalletAlreadyExists.class);
    }

    @Test
    @DisplayName("Test for deposit success")
    void depositSuccess() throws UserNotFound, AmountGreaterThanZero, WalletNotFound {
        Assertions.assertThat(walletService.deposit("7014219283", 1000.0)).isEqualTo("Deposit successful");
    }

    @Test
    @DisplayName("Test for deposit where user not found")
    void depositUserNotFound() {
        Assertions.assertThatThrownBy(() -> walletService.deposit("8875019992", 1000.0)).isInstanceOf(UserNotFound.class);
    }

    @Test
    @DisplayName("Test for deposit where amount is less than 0")
    void depositAmountGreaterThan0() {
        Assertions.assertThatThrownBy(() -> walletService.deposit("7014219283", -1000.0)).isInstanceOf(AmountGreaterThanZero.class);
    }

    @Test
    @DisplayName("Test for deposit where user not found")
    void depositWalletNotFound() {
        Assertions.assertThatThrownBy(() -> walletService.deposit("9027256094", 1000.0)).isInstanceOf(WalletNotFound.class);
    }


//    @Test
//    @DisplayName("Test for Wallet Transfer")
//    void transferSuccess() throws UserNotFound, WalletAlreadyExists, AmountGreaterThanZero, WalletNotFound, InsufficientBalance {
//        walletService.createWallet("8875019993");
//        walletService.deposit("8875019993", 1000.0);
//        Assertions.assertThat(walletService.transfer("8875019993", "7014219283", 300.0)).isEqualTo("Money transferred successfully");
//    }
}