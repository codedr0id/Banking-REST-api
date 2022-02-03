package paytm.assignment.Controllers;

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

    @Autowired
    private UserService service;

    //List of Users
    @GetMapping("/users")
    public List<User> list() {
        return service.listAll();
    }

    //GET method with validations
    @GetMapping("/users/{username}")
    public ResponseEntity<ResponseObject> get(@PathVariable String username) {
        User user = null;
        try {
            user = service.get(username);
            return new ResponseEntity<>(new ResponseObject(HttpStatus.OK.value(), "User found successfully", user), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ResponseObject(HttpStatus.NOT_FOUND.value(), "User not found", user), HttpStatus.NOT_FOUND);
        }
    }

    //POST method with validations
    @PostMapping("/user")
    public ResponseEntity<ResponseObject> add(@RequestBody User user) {
        String err = user.check();
        if (service.duplicates(user)) {
            return new ResponseEntity<>(new ResponseObject(HttpStatus.FORBIDDEN.value(), "User already exists", user), HttpStatus.FORBIDDEN);
        } else if (!Objects.equals(err, "ok")) {
            return new ResponseEntity<>(new ResponseObject(HttpStatus.FORBIDDEN.value(), "Please enter " + err, user), HttpStatus.FORBIDDEN);
        } else {
            service.save(user);
            return new ResponseEntity<>(new ResponseObject(HttpStatus.OK.value(), "User created successfully", user), HttpStatus.OK);

        }
    }

    //PUT method with validations
    @PutMapping("/user/{username}")
    public ResponseEntity<ResponseObject> update(@RequestBody User user, @PathVariable String username) {

        try {
            service.save(user);
            return new ResponseEntity<>(new ResponseObject(HttpStatus.OK.value(), "User updated successfully", user), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ResponseObject(HttpStatus.NOT_FOUND.value(), "User not found", null), HttpStatus.NOT_FOUND);
        }
    }

    //DELETE method with validations
    @DeleteMapping("/user/{username}")
    public ResponseEntity<ResponseObject> delete(@PathVariable String username) {
        try {
            User existUser = service.get(username);
            service.delete(username);
            return new ResponseEntity<>(new ResponseObject(HttpStatus.OK.value(), "User found successfully", existUser), HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ResponseObject(HttpStatus.NOT_FOUND.value(), "User not found, so cannot delete", null), HttpStatus.NOT_FOUND);
        }
    }
}
