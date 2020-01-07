package domain.drive;

import domain.user.User;

import java.util.List;

public interface DriveService {
    List<Drive> getAll();
    Drive getSingle(String name);
    List<Drive> getAllDrivesFromSameOrganization(User user);
    void post(Drive drive);
    void put(String name, Drive drive);
    void delete(String name);
}
