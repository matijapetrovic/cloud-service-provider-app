package api.user;

import domain.user.User;
import domain.user.UserService;
import domain.user.UserStorage;

import java.util.List;
import java.util.Optional;

import static spark.Spark.halt;

class UserRESTService implements UserService {
    private UserStorage storage;

    public UserRESTService(UserStorage storage) {
        this.storage = storage;
    }

    @Override
    public List<User> getAll() {
        return (List<User>) storage.findAll();
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
        if (!storage.findByName(email).isPresent())
            halt(404, "User with the email "
                    + email + " not found");
        if (!storage.update(email, user))
            halt(400, "Email "
                    + user.getEmail() + " already taken");
    }

    @Override
    public void delete(String email) {
        if (!storage.delete(email))
            halt(404, "User with the email "
                    + email + " not found");
    }
}
