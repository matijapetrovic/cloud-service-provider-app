package authentication;

import models.User;

public class TokenResponse {
    private boolean authenticated;
    private String token;
    private UserResponse userResponse;

    public TokenResponse(boolean authenticated, String token, UserResponse userResponse) {
        this.authenticated = authenticated;
        this.token = token;
        this.userResponse = userResponse;
    }
}
