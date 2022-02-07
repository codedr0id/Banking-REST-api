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
import paytm.assignment.Models.User;
import paytm.assignment.Services.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    UserService userService;

    User user1, user2, user3;

    @BeforeEach
    void create() throws IOException {
        String user1Req = "src/test/java/paytm/assignment/json/User1Req.json";
        String requestJSON1 = new String(Files.readAllBytes(Paths.get(user1Req)));

        String user2Req = "src/test/java/paytm/assignment/json/User2Req.json";
        String requestJSON2 = new String(Files.readAllBytes(Paths.get(user2Req)));

        String user3Req = "src/test/java/paytm/assignment/json/User3Req.json";
        String requestJSON3 = new String(Files.readAllBytes(Paths.get(user3Req)));

        user1 = new ObjectMapper().readValue(requestJSON1, User.class);
        user2 = new ObjectMapper().readValue(requestJSON2, User.class);
        user3 = new ObjectMapper().readValue(requestJSON3, User.class);
    }

    @Test
    @Order(1)
    @DisplayName("List all users")
    public void list() throws Exception {
        Mockito.when(userService.listAll()).thenReturn(Stream.of(user1, user2)
                .collect(java.util.stream.Collectors.toList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(userService.listAll())));
    }

    @Test
    @Order(2)
    @DisplayName("Get user by Username")
    public void getUser() throws Exception {
        ResponseObject res = new ResponseObject(HttpStatus.OK.value(), "User found successfully",
                new User("godfather", "Mehul", "Lathi", "mehullathi@gmail.com", "8875019993", "Udaipur", "Raj", false));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/godfather"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(res)));
    }

    @Test
    @Order(3)
    @DisplayName("Create User")
    public void createUser() throws Exception {
        ResponseObject res = new ResponseObject(HttpStatus.OK.value(), "User created successfully", user3);
        User user = user3;

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(res)));
    }

    @Test
    @Order(4)
    @DisplayName("Create User Failure")
    public void createUserFailure() throws Exception {
        ResponseObject res = new ResponseObject(HttpStatus.FORBIDDEN.value(), "User already exists", user1);
        User user = user1;

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(res)));
    }
}
