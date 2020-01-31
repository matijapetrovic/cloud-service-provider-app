package storage.json_storage.drive;

import domain.drive.Drive;
import domain.virtual_machine.VirtualMachine;
import storage.json_storage.JSONDbContext;

public class DriveReferenceBuilder {
    private JSONDbContext context;

    public DriveReferenceBuilder(JSONDbContext context) {
        this.context = context;
    }

    public void buildReferences(Drive drive) {
        buildVirtualMachineReferences(drive);
        buildOrganizationReferences(drive);
    }

    private void buildVirtualMachineReferences(Drive drive) {
        if (drive.getVm() != null)
            drive.setVm(context.getVirtualMachinesRepository()
                    .findByKey(drive.getVm().getName())
                    .orElse(null));
    }

    private void buildOrganizationReferences(Drive drive) {
        drive.setOrganization(context.getOrganizationsRepository()
                .findByKey(drive.getOrganization().getName())
                .orElse(null));
    }
}
