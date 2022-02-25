package paytm.assignment.DTO;

public class ResponseObject {

    private int statusCode;
    private String message;
    private Object response;

    public ResponseObject(int statusCode, String message, Object response) {
        this.statusCode = statusCode;
        this.message = message;
        this.response = response;
    }

    public ResponseObject() {

    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
