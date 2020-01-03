package storage.user;

import domain.user.User;
import storage.JSONFileRepository;
import storage.user.serializing.UserSerializer;

public class UserRepository extends JSONFileRepository<String, User> {
    public UserRepository(String filePath) {
        super(new UserSerializer(), filePath);
    }
}
