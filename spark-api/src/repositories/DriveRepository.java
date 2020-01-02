package repositories;

import models.Drive;
import serializer.drive.DriveSerializer;

public class DriveRepository extends JSONFileRepository<String, Drive>{
    public DriveRepository(String filePath) {
        super(new DriveSerializer(), filePath);
    }
}
