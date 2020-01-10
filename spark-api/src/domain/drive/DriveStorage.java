package domain.drive;

import java.util.Optional;

public interface DriveStorage {
    Iterable<Drive> findAll();
    Optional<Drive> findByName(String name);
    boolean add(Drive entity);
    boolean update(String name, Drive entity);
    boolean delete(String name);
}
