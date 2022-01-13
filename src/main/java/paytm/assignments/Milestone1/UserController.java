package paytm.assignments.Milestone1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/users")
    public List<User> list() {
        return service.listAll();
    }

    @GetMapping("/users/{username}")
    public ResponseEntity get(@PathVariable String username) {
        try {
            User user = service.get(username);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username does not exist.");
        }
    }

    @PostMapping("/user")
    public ResponseEntity add(@RequestBody User user){
        String err = user.check();
        if (service.duplicates(user)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User already exists");
        }
        else if(err != "ok"){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please enter "+err);
        }
        else{
            service.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

    @PutMapping("/user/{username}")
    public ResponseEntity update(@RequestBody User user, @PathVariable String username) {

        try {
            User existUser = service.get(username);
            service.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @DeleteMapping("/user/{username}")
    public String delete(@PathVariable String username){
        try {
            User existUser = service.get(username);
            service.delete(username);
            return "User deleted successfully";
        } catch (NoSuchElementException e) {
            return "User not found";
        }
    }
}
