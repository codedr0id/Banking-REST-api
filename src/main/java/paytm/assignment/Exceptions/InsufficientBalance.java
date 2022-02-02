package paytm.assignment.Exceptions;

public class InsufficientBalance extends Exception{
    public InsufficientBalance(){
        super("Insufficient Balance");
    }
}
