package domain.user;

import java.util.List;

public interface UserService {
    List<User> getAll();
    List<User> getAllUsersFromSameOrganization(User user);
    User getSingle(String email);
    void post(User user);
    void put(String email, User user);
    void delete(String email);
}
