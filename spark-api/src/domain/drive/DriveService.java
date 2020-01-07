package domain.drive;

import java.util.List;

public interface DriveService {
    List<Drive> getAll();
    Drive getSingle(String name);
    void post(Drive drive);
    void put(String name, Drive drive);
}
