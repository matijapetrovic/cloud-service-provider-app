package storage.json_storage.virtual_machine.serializing;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import domain.drive.Drive;

public class VirtualMachineExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes field) {
        if (field.getDeclaringClass() == Drive.class &&
                !field.getName().equals("name"))
            return true;

        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
