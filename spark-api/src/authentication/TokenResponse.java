package authentication;


public class TokenResponse {
    private boolean authenticated;
    private String token;
    private UserResponse user;

    public TokenResponse(boolean authenticated, String token, UserResponse user) {
        this.authenticated = authenticated;
        this.token = token;
        this.user = user;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getToken() {
        return token;
    }

    public UserResponse getUser() {
        return user;
    }
}
