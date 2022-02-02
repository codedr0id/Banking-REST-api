package paytm.assignment.DTO;

public class TransferDetails {
    private String payer;
    private String payee;
    private Double amount;

    public TransferDetails(String payer, String payee, Double amount) {
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
