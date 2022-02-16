package paytm.assignment.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import paytm.assignment.DTO.ResponseObject;
import paytm.assignment.Models.Wallet;
import paytm.assignment.Services.WalletService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WalletControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    WalletService walletService;

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
    @Order(1)
    @DisplayName("Create Wallet")
    public void createWallet() throws Exception {
        ResponseObject res1 = new ResponseObject(HttpStatus.CREATED.value(), "Wallet created", wallet1);
        mockMvc.perform(MockMvcRequestBuilders.post("/create-wallet/" + wallet1.getMobileNo())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(res1)));

        ResponseObject res2 = new ResponseObject(HttpStatus.CREATED.value(), "Wallet created", wallet2);
        mockMvc.perform(MockMvcRequestBuilders.post("/create-wallet/" + wallet2.getMobileNo())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(res2)));
    }

    @Test
    @Order(2)
    @DisplayName("List all wallets")
    public void listWallets() throws Exception {
        Mockito.when(walletService.getAllWallets()).thenReturn(Stream.of(wallet1, wallet2)
                .collect(java.util.stream.Collectors.toList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/wallets"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(walletService.getAllWallets())));
    }

    @Test
    @Order(3)
    @DisplayName("Get wallet by MobileNo")
    public void getWallet() throws Exception {
        Mockito.when(walletService.getWallet(wallet1.getMobileNo())).thenReturn(wallet1);

        mockMvc.perform(MockMvcRequestBuilders.get("/wallet/" + wallet1.getMobileNo()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(walletService.getWallet(wallet1.getMobileNo()))));
    }

    @Test
    @Order(4)
        @DisplayName("Deposit amount in wallet")
    public void deposit() throws Exception {
        ResponseObject res = new ResponseObject(HttpStatus.OK.value(), "Money added", new Wallet(wallet1.getMobileNo(), wallet1.getBalance() + 100.0));

        mockMvc.perform(MockMvcRequestBuilders.post("/deposit/" + wallet1.getMobileNo() + "/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(res)));
    }
}
