package paytm.assignments.Milestone1;

import org.springframework.data.jpa.repository.JpaRepository;

//Repository Interface
public interface UserRepository extends JpaRepository<User, String> {
}
