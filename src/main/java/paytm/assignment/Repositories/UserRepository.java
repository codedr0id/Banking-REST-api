package paytm.assignment.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import paytm.assignment.Models.User;

//Repository Interface
public interface UserRepository extends JpaRepository<User, String> {
}
