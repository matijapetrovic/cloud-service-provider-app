package storage.json_storage.virtual_machine.serializing;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import domain.drive.Drive;
import domain.organization.Organization;
import domain.vm_category.VMCategory;

public class VirtualMachineExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes field) {
        if (field.getDeclaringClass() == Organization.class &&
                !field.getName().equals("name"))
            return true;
        if (field.getDeclaringClass() == Drive.class &&
                !field.getName().equals("name"))
            return true;

        if (field.getDeclaringClass() == VMCategory.class &&
                !field.getName().equals("name"))
            return true;

        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
