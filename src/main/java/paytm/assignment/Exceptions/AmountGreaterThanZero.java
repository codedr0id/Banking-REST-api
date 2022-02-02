package paytm.assignment.Exceptions;

public class AmountGreaterThanZero extends Exception{
    public AmountGreaterThanZero(){
        super("Amount should be greater than 0");
    }
}
