package paytm.assignment.Exceptions;

public class WalletNotFound extends Exception {
    public WalletNotFound(String user) {
        super("Create " + user + " wallet first");
    }
}
