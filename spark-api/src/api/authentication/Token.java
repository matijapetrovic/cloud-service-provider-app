package api.authentication;

public class Token {
    private String email;
    private long timestamp;

    public Token() {

    }

    public Token(String email, long timestamp) {
        this.email = email;
        this.timestamp = timestamp;
    }

    public String getEmail() {
        return email;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
