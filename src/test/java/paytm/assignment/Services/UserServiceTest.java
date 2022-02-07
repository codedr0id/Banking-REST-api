package paytm.assignment.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import paytm.assignment.Models.User;
import paytm.assignment.Repositories.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    User user1, user2;

    @BeforeEach
    void create() throws IOException {
        String user1Req = "src/test/java/paytm/assignment/json/User1Req.json";
        String requestJSON1 = new String(Files.readAllBytes(Paths.get(user1Req)));

        String user2Req = "src/test/java/paytm/assignment/json/User2Req.json";
        String requestJSON2 = new String(Files.readAllBytes(Paths.get(user2Req)));

        user1 = new ObjectMapper().readValue(requestJSON1, User.class);
        user2 = new ObjectMapper().readValue(requestJSON2, User.class);
    }

    @Test
    @DisplayName("Test for listing all Users")
    void listAll() {
        Mockito.when(userRepository.findAll())
                .thenReturn(Stream.of(user1, user2)
                        .collect(java.util.stream.Collectors.toList()));
        assertEquals(2, userService.listAll().size());
    }

    @Test
    @DisplayName("Test for saving Users")
    void save() {
        User user = user1;
        Mockito.when(userRepository.save(user)).thenReturn(user);
        assertEquals(user, userService.save(user));
    }

    @Test
    @DisplayName("Test for getting user by id")
    void getByUsername() {
        User user = user1;
        Mockito.when(userRepository.findById(user.getUsername())).thenReturn(Optional.of(user));
        assertEquals(user, userService.get(user.getUsername()));
    }

    @Test
    @DisplayName("Test for getting user by Phone No.")
    void getByPhoneNo() {
        User user = user2;
        Mockito.when(userRepository.findByMobile(user.getMobile())).thenReturn(user);
        assertEquals(user, userService.getByPhoneNo(user.getMobile()));
    }

    @Test
    @DisplayName("Test for deleting user")
    void delete() {
        User user = user2;
        userService.delete(user.getUsername());
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(user.getUsername());
    }
}