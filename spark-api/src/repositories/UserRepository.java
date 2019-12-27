package repositories;

import models.User;
import serializer.user.UserSerializer;

public class UserRepository extends JSONFileRepository<String, User> {
    public UserRepository(String filePath) {
        super(new UserSerializer(), filePath);
    }
}
