package serializer.drive;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import models.User;
import models.VirtualMachine;

public class DriveExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes field) {
        // Treba da pogledam da li treba da se preskace Virtualna Maasina, u zavisnosti da li je gledam kao id ili referencu

        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
