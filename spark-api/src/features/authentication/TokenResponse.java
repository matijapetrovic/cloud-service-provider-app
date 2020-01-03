package features.authentication;


public class TokenResponse {
    private boolean authenticated;
    private String token;
    private UserResponse userResponse;

    public TokenResponse(boolean authenticated, String token, UserResponse userResponse) {
        this.authenticated = authenticated;
        this.token = token;
        this.userResponse = userResponse;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getToken() {
        return token;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }
}
