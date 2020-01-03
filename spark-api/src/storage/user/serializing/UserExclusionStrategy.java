package storage.user.serializing;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import domain.organization.Organization;

public class UserExclusionStrategy implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            if(field.getDeclaringClass() == Organization.class &&  !field.getName().equals("name"))
                return true;

            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
}
