package paytm.assignment.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paytm.assignment.Models.User;
import paytm.assignment.Repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository repo;

    //listing all the Users
    public List<User> listAll() {
        return repo.findAll();
    }

    //Saving User to DB
    public void save(User user) {
        repo.save(user);
    }

    //finding User by username
    public User get(String username) {
        return repo.findById(username).get();
    }

    //Deleting User from DB
    public void delete(String username) {
        repo.deleteById(username);
    }

    //check for duplicates
    public boolean duplicates(User user){
        return repo.existsById(user.getUsername()) || repo.existsById(user.getEmail()) || repo.existsById(user.getMobile());
    }
}
