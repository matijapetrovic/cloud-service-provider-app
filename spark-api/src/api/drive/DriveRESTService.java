package api.drive;

import domain.drive.Drive;
import domain.drive.DriveStorage;
import domain.drive.DriveService;
import domain.user.User;

import java.util.List;
import java.util.Optional;

import static spark.Spark.halt;

class DriveRESTService implements DriveService {
    private DriveStorage storage;

    public DriveRESTService(DriveStorage storage) {
        this.storage = storage;
    }

    @Override
    public List<Drive> getAll() {
        return (List<Drive>) storage.findAll();
    }

    @Override
    public Drive getSingle(String name) {
        Optional<Drive> drive = storage.findByName(name);
        if (!drive.isPresent())
            halt(404, "Drive with the name "
                    + name + " not found");

        return drive.get();
    }

    @Override
    public List<Drive> getAllDrivesFromSameOrganization(User user) {
        return null;
    }

    @Override
    public void post(Drive drive) {
        if (!storage.add(drive))
            halt(409, "Drive with the name "
                    + drive.getName() + " already exists");
    }

    @Override
    public void put(String name, Drive drive) {
        if (!storage.update(name, drive))
            halt(404, "Drive with the name "
                    + name + " not found");
    }

    @Override
    public void delete(String name) {
        if (!storage.delete(name))
            halt(404, "Drive with the name "
                    + name + " not found");
    }
}
