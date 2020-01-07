package storage;

import application.App;
import application.Utility;
import domain.drive.Drive;
import domain.organization.Organization;
import domain.user.User;
import domain.virtual_machine.VirtualMachine;
import domain.vm_category.VMCategory;
import storage.drive.serializing.DriveSerializer;
import storage.organization.serializing.OrganizationSerializer;
import storage.user.serializing.UserSerializer;
import storage.virtual_machine.serializing.VMSerializer;
import storage.vm_category.serializing.CategorySerializer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public class JSONDbContext {
    JSONFileRepository<String, Organization> organizations;
    JSONFileRepository<String, User> users;
    JSONFileRepository<String, VirtualMachine> virtualMachines;
    JSONFileRepository<String, Drive> drives;
    JSONFileRepository<String, VMCategory> vmCategories;

    public JSONDbContext(String directoryName) {
        organizations = new JSONFileRepository<>(new OrganizationSerializer(),
                Utility.joinPath(directoryName, "organizations.json"));
        users = new JSONFileRepository<>(new UserSerializer(),
                Utility.joinPath(directoryName, "users.json"));
        virtualMachines = new JSONFileRepository<>(new VMSerializer(),
                Utility.joinPath(directoryName, "vms.json"));
        drives = new JSONFileRepository<>(new DriveSerializer(),
                Utility.joinPath(directoryName, "drives.json"));
        vmCategories = new JSONFileRepository<>(new CategorySerializer(),
                Utility.joinPath(directoryName, "categories.json"));
    }



}
