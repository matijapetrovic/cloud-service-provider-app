package api.user;

import domain.organization.Organization;
import domain.user.User;

public class UserDTO {
    public enum Role
    {
        USER ,
        ADMIN ,
        SUPER_ADMIN;
    };
    private String email;
    private String password;
    private String name;
    private String surname;
    private String organization;
    private User.Role role;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}
