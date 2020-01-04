package serializer.drive;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import models.Organization;
import models.User;
import models.VirtualMachine;

public class DriveExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes field) {
        if(field.getDeclaringClass() == VirtualMachine.class &&  !field.getName().equals("name"))
            return true;

        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
