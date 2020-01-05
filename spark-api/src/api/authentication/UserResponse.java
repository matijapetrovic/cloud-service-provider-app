package api.authentication;

import domain.user.User;

public class UserResponse {
    private String email;
    private User.Role role;

    public UserResponse() {

    }

    public UserResponse(User user) {
        this.email = user.getEmail();
        this.role = user.getRole();
    }

    public String getEmail() {
        return email;
    }

    public User.Role getRole() {
        return role;
    }
}
