package serializer.user;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import models.Organization;
import models.User;

public class UserExclusionStrategy implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            return (field.getDeclaringClass() == Organization.class &&
                    !field.getName().equals("name"));

    }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
}
