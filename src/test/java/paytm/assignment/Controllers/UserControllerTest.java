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

    @Test
    @Order(1)
    @DisplayName("List all users")
    public void list() throws Exception {
        Mockito.when(userService.listAll()).thenReturn(Stream.of(new User("godfather", "Mehul", "Lathi", "mehullathi@gmail.com", "8875019993", "Udaipur", "Raj", false),
                        new User("sparky", "Ujjwal", "Shrivastava", "ujjwal@gmail.com", "9027256094", "Meerut", "UP", false),
                        new User("raghu", "Raghav", "Lathi", "raghav@gmail.com", "7014219283", "Udaipur", "Raj", true))
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
        ResponseObject res = new ResponseObject(HttpStatus.OK.value(), "User created successfully",
                new User("eagle", "Kartikay", "Sarswat", "kartikay@gmail.com", "1234567890", "Ganganagar", "Raj", false));

        User user = new User("eagle", "Kartikay", "Sarswat", "kartikay@gmail.com", "1234567890", "Ganganagar", "Raj", false);

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
        ResponseObject res = new ResponseObject(HttpStatus.FORBIDDEN.value(), "User already exists",
                new User("eagle", "Kartikay", "Sarswat", "kartikay@gmail.com", "1234567890", "Ganganagar", "Raj", false));

        User user = new User("eagle", "Kartikay", "Sarswat", "kartikay@gmail.com", "1234567890", "Ganganagar", "Raj", false);

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(res)));
    }
}
