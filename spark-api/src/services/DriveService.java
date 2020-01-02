package services;

import models.Drive;
import models.User;
import repositories.DriveRepository;

import java.util.Collection;


public class DriveService extends Service<String, Drive> {
    public DriveService() {
        super(new DriveRepository("./data/drives.json"));
    }

    public Collection<Drive> getAllDrivesFromSameOrganization(User user){
        return user.getOrganization().getDrives();
    }
}
