package paytm.assignment.Services;

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

    @Test
    @DisplayName("Test for listing all Users")
    void listAll() {
        Mockito.when(userRepository.findAll())
                .thenReturn(Stream.of(new User("godfather", "Mehul", "Lathi", "mehullathi@gmail.com", "8875019993", "Udaipur", "Raj", false),
                                new User("sparky", "Ujjwal", "Shrivastava", "ujjwal@gmail.com", "9027256094", "Meerut", "UP", false))
                        .collect(java.util.stream.Collectors.toList()));
        assertEquals(2, userService.listAll().size());
    }

    @Test
    @DisplayName("Test for saving Users")
    void save() {
        User user = new User("godfather", "Mehul", "Lathi", "mehullathi@gmail.com", "8875019993", "Udaipur", "Raj", false);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        assertEquals(user, userService.save(user));
    }

    @Test
    @DisplayName("Test for getting user by id")
    void getByUsername() {
        User user = new User("godfather", "Mehul", "Lathi", "mehullathi@gmail.com", "8875019993", "Udaipur", "Raj", false);
        Mockito.when(userRepository.findById(user.getUsername())).thenReturn(Optional.of(user));
        assertEquals(user, userService.get(user.getUsername()));
    }

    @Test
    @DisplayName("Test for getting user by Phone No.")
    void getByPhoneNo() {
        User user = new User("sparky", "Ujjwal", "Shrivastava", "ujjwal@gmail.com", "9027256094", "Meerut", "UP", false);
        Mockito.when(userRepository.findByMobile(user.getMobile())).thenReturn(user);
        assertEquals(user, userService.getByPhoneNo(user.getMobile()));
    }

    @Test
    @DisplayName("Test for deleting user")
    void delete() {
        User user = new User("sparky", "Ujjwal", "Shrivastava", "ujjwal@gmail.com", "9027256094", "Meerut", "UP", false);
        userService.delete(user.getUsername());
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(user.getUsername());
    }
}