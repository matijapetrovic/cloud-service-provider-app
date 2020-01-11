package storage.json_storage.virtual_machine;

import domain.virtual_machine.VirtualMachine;
import storage.json_storage.JSONDbContext;

public class VirtualMachineReferenceBuilder {
    private JSONDbContext context;

    public VirtualMachineReferenceBuilder(JSONDbContext context) {
        this.context = context;
    }

    public void buildReferences(VirtualMachine virtualMachine) {
            buildDriveReferences(virtualMachine);
    }

    public void buildDriveReferences(VirtualMachine virtualMachine) {
        for (int i = 0; i < virtualMachine.getDrives().size(); i++)
            virtualMachine.updateDrive(i, context.getDrivesRepository()
                    .findByKey(virtualMachine
                            .getDrives()
                            .get(i)
                            .getName())
                    .orElse(null));
    }
}
