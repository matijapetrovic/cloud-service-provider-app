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
            buildCategoryReference(virtualMachine);
            buildOrganizationReference(virtualMachine);
    }

    private void buildDriveReferences(VirtualMachine virtualMachine) {
        for (int i = 0; i < virtualMachine.getDrives().size(); i++)
            virtualMachine.updateDrive(i, context.getDrivesRepository()
                    .findByKey(virtualMachine
                            .getDrives()
                            .get(i)
                            .getName())
                    .orElse(null));
    }

    private void buildOrganizationReference(VirtualMachine virtualMachine) {
        virtualMachine.setOrganization(context.
                getOrganizationsRepository()
                .findByKey(virtualMachine
                            .getOrganization()
                            .getName())
                .orElse(null));
    }

    private void buildCategoryReference(VirtualMachine virtualMachine) {
        virtualMachine.setCategory(context.getVmCategoriesRepository()
                .findByKey(virtualMachine
                            .getCategory()
                            .getName())
                .orElse(null));
    }
}
