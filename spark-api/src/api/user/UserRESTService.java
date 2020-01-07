package api.user;

import domain.user.User;
import domain.user.UserService;
import domain.user.UserStorage;

import java.util.List;
import java.util.Optional;

import static spark.Spark.halt;

public class UserRESTService implements UserService {
    private UserStorage storage;

    public UserRESTService(UserStorage storage) {
        this.storage = storage;
    }

    @Override
    public List<User> getAll() {
        return (List<User>) storage.findAll();
    }

    @Override
    public List<User> getAllUsersFromSameOrganization(String email) {
        Optional<User> user = storage.findByName(email);
        if (!user.isPresent())
            halt(404, "User with the email "
                    + email + " not found");

        return user.get().getOrganization().getUsers();
    }

    @Override
    public User getSingle(String email) {
        Optional<User> user = storage.findByName(email);
        if (!user.isPresent())
            halt(404, "User with the email "
                    + email + " not found");

        return user.get();
    }

    @Override
    public void post(User user) {
        if (!storage.add(user))
            halt(409, "User with the email "
                    + user.getEmail() + " already exists");
    }

    @Override
    public void put(String email, User user) {
        if (!storage.update(email, user))
            halt(404, "User with the email "
                    + email + " not found");
    }

    @Override
    public void delete(String email) {
        if (!storage.delete(email))
            halt(404, "User with the email "
                    + email + " not found");
    }
}
