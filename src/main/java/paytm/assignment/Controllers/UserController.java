package paytm.assignment.Controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paytm.assignment.DTO.ResponseObject;
import paytm.assignment.Models.User;
import paytm.assignment.Services.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
public class UserController {

    Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService service;

    //List of Users
    @GetMapping("/users")
    public List<User> list() {
        logger.info("Showing List of all Users");
        return service.listAll();
    }

    //GET method with validations
    @GetMapping("/users/{username}")
    public ResponseEntity<ResponseObject> get(@PathVariable String username) {
        logger.info("Fetching User with username: " + username);
        User user = null;
        try {
            user = service.get(username);
            logger.debug("User - " + username + " found successfully");
            return new ResponseEntity<>(new ResponseObject(HttpStatus.OK.value(), "User found successfully", user), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            logger.error("User - " + username + " not found");
            return new ResponseEntity<>(new ResponseObject(HttpStatus.NOT_FOUND.value(), "User not found", user), HttpStatus.NOT_FOUND);
        }
    }

    //POST method with validations
    @PostMapping("/user")
    public ResponseEntity<ResponseObject> add(@RequestBody User user) {
        logger.info("Adding new User with username: " + user.getUsername());
        String err = user.check();
        if (service.duplicates(user)) {
            logger.error("User with username: " + user.getUsername() + " already exists");
            return new ResponseEntity<>(new ResponseObject(HttpStatus.FORBIDDEN.value(), "User already exists", user), HttpStatus.FORBIDDEN);
        } else if (!Objects.equals(err, "ok")) {
            return new ResponseEntity<>(new ResponseObject(HttpStatus.FORBIDDEN.value(), "Please enter " + err, user), HttpStatus.FORBIDDEN);
        } else {
            service.save(user);
            logger.debug("User with username: " + user.getUsername() + " added successfully");
            return new ResponseEntity<>(new ResponseObject(HttpStatus.OK.value(), "User created successfully", user), HttpStatus.OK);

        }
    }

    //PUT method with validations
    @PutMapping("/user/{username}")
    public ResponseEntity<ResponseObject> update(@RequestBody User user, @PathVariable String username) {
        logger.info("Updating User with username: " + username);
        try {
            logger.debug("User with username: " + username + " updated successfully");
            service.save(user);
            return new ResponseEntity<>(new ResponseObject(HttpStatus.OK.value(), "User updated successfully", user), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            logger.error("User with username: " + username + " not found");
            return new ResponseEntity<>(new ResponseObject(HttpStatus.NOT_FOUND.value(), "User not found", null), HttpStatus.NOT_FOUND);
        }
    }

    //DELETE method with validations
    @DeleteMapping("/user/{username}")
    public ResponseEntity<ResponseObject> delete(@PathVariable String username) {
        logger.info("Deleting User with username: " + username);
        try {
            User existUser = service.get(username);
            service.delete(username);
            logger.debug("User with username: " + username + " deleted successfully");
            return new ResponseEntity<>(new ResponseObject(HttpStatus.OK.value(), "User found successfully", existUser), HttpStatus.OK);

        } catch (NoSuchElementException e) {
            logger.error("User with username: " + username + " not found");
            return new ResponseEntity<>(new ResponseObject(HttpStatus.NOT_FOUND.value(), "User not found, so cannot delete", null), HttpStatus.NOT_FOUND);
        }
    }
}
