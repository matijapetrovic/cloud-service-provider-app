package storage.vm_category.serializing;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class CategoryExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes field) {
        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
