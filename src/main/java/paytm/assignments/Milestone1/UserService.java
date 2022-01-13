package paytm.assignments.Milestone1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository repo;

    public List<User> listAll() {
        return repo.findAll();
    }

    //Function for saving the user
    public void save(User user) {
        repo.save(user);
    }

    //Function for finding the user by id
    public User get(String username) {
        return repo.findById(username).get();
    }

    //Function for deleting the user
    public void delete(String username) {
        repo.deleteById(username);
    }
}
