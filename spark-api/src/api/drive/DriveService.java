package api.drive;

import api.Service;
import domain.drive.Drive;
import domain.user.User;
import storage.drive.DriveRepository;

import java.util.Collection;


public class DriveService extends Service<String, Drive> {
    public DriveService() {
        super(new DriveRepository("./data/drives.json"));
    }

    public Collection<Drive> getAllDrivesFromSameOrganization(User user){
        return user.getOrganization().getDrives();
    }
}
