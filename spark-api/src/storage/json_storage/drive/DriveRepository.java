package storage.json_storage.drive;

import domain.drive.Drive;
import storage.json_storage.JSONFileRepository;
import storage.json_storage.drive.serializing.DriveSerializer;

public class DriveRepository extends JSONFileRepository<String, Drive> {
    public DriveRepository(String filePath) {
        super(new DriveSerializer(), filePath);
    }
}
