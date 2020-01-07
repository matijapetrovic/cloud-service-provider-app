package storage.drive;

import domain.drive.Drive;
import storage.JSONFileRepository;
import storage.drive.serializing.DriveSerializer;

public class DriveRepository extends JSONFileRepository<String, Drive> {
    public DriveRepository(String filePath) {
        super(new DriveSerializer(), filePath);
    }
}
