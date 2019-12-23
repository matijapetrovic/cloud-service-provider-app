package authentication;

import models.User;

public class TokenResponse {
    private boolean authenticated;
    private String token;
    private User user;

    public TokenResponse(boolean authenticated, String token) {
        this.authenticated = authenticated;
        this.token = token;
        this.user = null;
    }
}
