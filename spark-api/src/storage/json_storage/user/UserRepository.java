package storage.json_storage.user;

import domain.user.User;
import storage.json_storage.JSONFileRepository;
import storage.json_storage.user.serializing.UserSerializer;

public class UserRepository extends JSONFileRepository<String, User> {
    public UserRepository(String filePath) {
        super(new UserSerializer(), filePath);
    }
}
