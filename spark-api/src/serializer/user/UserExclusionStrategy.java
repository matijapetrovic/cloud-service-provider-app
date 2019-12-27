package serializer.user;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
<<<<<<< HEAD
=======
import models.User;
>>>>>>> develop

public class UserExclusionStrategy implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            return false;
    }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
}
