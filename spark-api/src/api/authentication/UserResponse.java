package api.authentication;

import domain.user.User;

public class UserResponse {
    private String email;
    private User.Role role;
    private String organization;

    public UserResponse() {

    }

    public UserResponse(User user) {
        this.email = user.getEmail();
        this.role = user.getRole();
        if (user.getRole() != User.Role.SUPER_ADMIN)
            this.organization = user.getOrganization().getName();
    }

    public String getEmail() {
        return email;
    }

    public User.Role getRole() {
        return role;
    }

    public String getOrganization() { return organization; }
}
