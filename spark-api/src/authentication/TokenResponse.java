package authentication;

public class TokenResponse {
    private boolean authenticated;
    private String token;

    public TokenResponse(boolean authenticated, String token) {
        this.authenticated = authenticated;
        this.token = token;
    }
}
