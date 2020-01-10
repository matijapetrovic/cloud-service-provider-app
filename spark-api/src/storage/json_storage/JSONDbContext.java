package storage.json_storage;

import application.Utility;

import domain.drive.Drive;
import domain.organization.Organization;
import domain.user.User;
import domain.virtual_machine.VirtualMachine;
import domain.vm_category.VMCategory;

import storage.json_storage.drive.serializing.DriveSerializer;
import storage.json_storage.organization.serializing.OrganizationSerializer;
import storage.json_storage.user.serializing.UserSerializer;
import storage.json_storage.virtual_machine.serializing.VirtualMachineSerializer;
import storage.json_storage.vm_category.serializing.CategorySerializer;

public class JSONDbContext {
    private JSONFileRepository<String, Organization> organizationsRepository;
    private JSONFileRepository<String, User> usersRepository;
    private JSONFileRepository<String, VirtualMachine> virtualMachinesRepository;
    private JSONFileRepository<String, Drive> drivesRepository;
    private JSONFileRepository<String, VMCategory> vmCategoriesRepository;

    public JSONDbContext(String directoryName) {
        initRepositories(directoryName);
        buildReferences();
    }

    private void initRepositories(String directoryName) {
        organizationsRepository = new JSONFileRepository<>(new OrganizationSerializer(),
                Utility.joinPath(directoryName, "organizations.json"));
        usersRepository = new JSONFileRepository<>(new UserSerializer(),
                Utility.joinPath(directoryName, "users.json"));
        virtualMachinesRepository = new JSONFileRepository<>(new VirtualMachineSerializer(),
                Utility.joinPath(directoryName, "vms.json"));
        drivesRepository = new JSONFileRepository<>(new DriveSerializer(),
                Utility.joinPath(directoryName, "drives.json"));
        vmCategoriesRepository = new JSONFileRepository<>(new CategorySerializer(),
                Utility.joinPath(directoryName, "categories.json"));
    }

    private void buildReferences() {
        buildUserReferences();
        buildOrganizationsReferences();
        buildVirtualMachineReferences();
        buildDriveReferences();
    }

    private void buildUserReferences() {
        usersRepository.data
                .forEach(
                        user ->  {
                            if (user.getRole() != User.Role.SUPER_ADMIN)
                                user.setOrganization(organizationsRepository
                                        .findByKey(user
                                                .getOrganization()
                                                .getName())
                                                .orElse(null));
                        });
    }

    private void buildOrganizationsReferences() {
        organizationsRepository.data
                .forEach(
                        organization -> {
                            for (int i = 0; i < organization.getUsers().size(); i++)
                                organization.updateUser(i, usersRepository
                                                            .findByKey(organization
                                                                    .getUsers()
                                                                    .get(i)
                                                                    .getEmail())
                                                                    .orElse(null));
                            for (int i = 0; i < organization.getVirtualMachines().size(); i++)
                                organization.updateVirtualMachine(i, virtualMachinesRepository
                                                            .findByKey(organization
                                                                    .getVirtualMachines()
                                                                    .get(i)
                                                                    .getName())
                                                                    .orElse(null));
                            for (int i = 0; i < organization.getDrives().size(); i++)
                                organization.updateDrive(i, drivesRepository
                                        .findByKey(organization
                                                .getDrives()
                                                .get(i)
                                                .getName())
                                        .orElse(null));
                        });
    }

    private void buildVirtualMachineReferences() {
        virtualMachinesRepository.data
                .forEach(
                        virtualMachine -> {
                            for (int i = 0; i < virtualMachine.getDrives().size(); i++)
                                virtualMachine.updateDrive(i, drivesRepository
                                        .findByKey(virtualMachine
                                                .getDrives()
                                                .get(i)
                                                .getName())
                                        .orElse(null));
                        });
    }

    private void buildDriveReferences() {
        drivesRepository.data
                .forEach(
                        drive -> {
                            drive.setVm(virtualMachinesRepository
                                    .findByKey(drive
                                            .getVm()
                                            .getName())
                                            .orElse(null));
                        });
    }

    public JSONFileRepository<String, Drive> getDrivesRepository() {
        return drivesRepository;
    }

    public JSONFileRepository<String, Organization> getOrganizationsRepository() {
        return organizationsRepository;
    }

    public JSONFileRepository<String, User> getUsersRepository() {
        return usersRepository;
    }

    public JSONFileRepository<String, VirtualMachine> getVirtualMachinesRepository() {
        return virtualMachinesRepository;
    }

    public JSONFileRepository<String, VMCategory> getVmCategoriesRepository() {
        return vmCategoriesRepository;
    }
}
