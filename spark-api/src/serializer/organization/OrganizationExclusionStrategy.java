package serializer.organization;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import models.User;
import models.VirtualMachine;

public class OrganizationExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes field) {
        if (field.getDeclaringClass() == User.class &&
                !field.getName().equals("email"))
            return true;

        if (field.getDeclaringClass() == VirtualMachine.class &&
                !field.getName().equals("name"))
            return true;

        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
