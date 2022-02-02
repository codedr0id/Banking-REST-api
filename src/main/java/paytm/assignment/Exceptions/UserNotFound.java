package paytm.assignment.Exceptions;

public class UserNotFound extends Exception {
    public UserNotFound(String user) {
        super(user + " not Found");
    }
}
