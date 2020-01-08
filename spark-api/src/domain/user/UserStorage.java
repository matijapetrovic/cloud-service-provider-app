package domain.user;

import java.util.Optional;

public interface UserStorage {
    Iterable<User> findAll();
    Optional<User> findByName(String name);
    boolean add(User entity);
    boolean update(String name, User entity);
    boolean delete(String name);
}
