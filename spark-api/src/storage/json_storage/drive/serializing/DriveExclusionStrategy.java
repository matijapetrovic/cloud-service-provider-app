package storage.json_storage.drive.serializing;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import domain.organization.Organization;
import domain.virtual_machine.VirtualMachine;
import domain.vm_category.VMCategory;

public class DriveExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes field) {
        if (field.getDeclaringClass() == VirtualMachine.class &&
                !field.getName().equals("name"))
            return true;

        if (field.getDeclaringClass() == Organization.class &&
                !field.getName().equals("name"))
            return true;

        return false;
    }


    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
