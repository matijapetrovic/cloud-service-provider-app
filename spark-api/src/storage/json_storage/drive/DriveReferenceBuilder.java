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
    }

    private void buildVirtualMachineReferences(Drive drive) {
        drive.setVm(context.getVirtualMachinesRepository()
                .findByKey(drive
                        .getVm()
                        .getName())
                .orElse(null));
    }



}
