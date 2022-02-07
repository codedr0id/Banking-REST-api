package paytm.assignment.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import paytm.assignment.Exceptions.UserNotFound;
import paytm.assignment.Exceptions.WalletAlreadyExists;
import paytm.assignment.Models.Wallet;
import paytm.assignment.Repositories.WalletRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @MockBean
    private WalletRepository walletRepository;

    @Autowired
    private WalletService walletService;

    Wallet wallet1, wallet2;

    @BeforeEach
    void create() throws IOException {

        String wallet1Req = "src/test/java/paytm/assignment/json/Wallet1Req.json";
        String requestJSON1 = new String(Files.readAllBytes(Paths.get(wallet1Req)));

        String wallet2Req = "src/test/java/paytm/assignment/json/Wallet2Req.json";
        String requestJSON2 = new String(Files.readAllBytes(Paths.get(wallet2Req)));

        wallet1 = new ObjectMapper().readValue(requestJSON1, Wallet.class);
        wallet2 = new ObjectMapper().readValue(requestJSON2, Wallet.class);
    }

    @Test
    @DisplayName("Test for getting a wallet")
    void getWallet() {

        Mockito.when(walletRepository.findByMobileNo(wallet1.getMobileNo())).thenReturn(wallet1);
        assertEquals(walletService.getWallet(wallet1.getMobileNo()), wallet1);
    }

    @Test
    @DisplayName("Test for getting all wallets")
    void getAllWallets() {
        Mockito.when(walletRepository.findAll()).thenReturn(java.util.Arrays.asList(wallet1, wallet2));
        assertEquals(walletService.getAllWallets().size(), 2);
    }

    @Test
    @DisplayName("Test for creating a new wallet")
    void createWallet() throws WalletAlreadyExists, UserNotFound {
        Mockito.when(walletRepository.save(wallet1)).thenReturn(wallet1);
        assertEquals(wallet1.toString(), walletService.createWallet(wallet1.getMobileNo()).toString());
    }

    @Test
    @DisplayName("Test for deleting a wallet")
    void delete() {
        walletService.deleteWallet(wallet1.getMobileNo());
        Mockito.verify(walletRepository, Mockito.times(1)).deleteById(wallet1.getMobileNo());
    }
}