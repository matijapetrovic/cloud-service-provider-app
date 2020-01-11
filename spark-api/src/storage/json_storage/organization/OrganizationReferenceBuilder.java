package storage.json_storage.organization;

import domain.organization.Organization;
import domain.virtual_machine.VirtualMachine;
import storage.json_storage.JSONDbContext;

public class OrganizationReferenceBuilder {
    private JSONDbContext context;

    public OrganizationReferenceBuilder(JSONDbContext context) {
        this.context = context;
    }

    public void buildReferences(Organization organization) {
        buildUserReferences(organization);
        buildVirtualMachineReferences(organization);
        buildDriveReferences(organization);
    }

    private void buildUserReferences(Organization organization) {
        for (int i = 0; i < organization.getUsers().size(); i++)
            organization.updateUser(i, context.getUsersRepository()
                    .findByKey(organization
                            .getUsers()
                            .get(i)
                            .getEmail())
                    .orElse(null));
    }

    private void buildVirtualMachineReferences(Organization organization) {
        for (int i = 0; i < organization.getVirtualMachines().size(); i++)
            organization.updateVirtualMachine(i, context.getVirtualMachinesRepository()
                    .findByKey(organization
                            .getVirtualMachines()
                            .get(i)
                            .getName())
                    .orElse(null));
    }

    private void buildDriveReferences(Organization organization) {
        for (int i = 0; i < organization.getDrives().size(); i++)
            organization.updateDrive(i, context.getDrivesRepository()
                    .findByKey(organization
                            .getDrives()
                            .get(i)
                            .getName())
                    .orElse(null));
    }
}
