package storage.json_storage.user.serializing;

import com.google.gson.GsonBuilder;

import domain.user.User;
import storage.json_storage.JSONSerializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UserSerializer extends JSONSerializer<User> {

    public UserSerializer() {
        gson = new GsonBuilder()
                .addSerializationExclusionStrategy(
                        new UserExclusionStrategy())
                .addDeserializationExclusionStrategy(
                        new UserExclusionStrategy())
                .create();
    }

    @Override
    public void serialize(Collection<User> data, FileWriter writer) {
        gson.toJson(data, writer);
    }

    @Override
    public List<User> deserialize(FileReader reader) {
        List<User> data = Arrays.asList(gson.fromJson(reader, User[].class));
//        buildReferences(data);
        return data;
    }
//
//    private void buildReferences(List<User> data) {
//        data.forEach(this::buildReferences);
//    }
//
//    private void buildReferences(User user) {
//        buildOrganizationReference(user);
//    }
//
//    private void buildOrganizationReference(User user) {
//        Organization org = null;
//        if(user.getRole()!= User.Role.SUPER_ADMIN){
//            org = App.organizationService.findByKey(user.getOrganization().getKey()).orElse(null);
//        }
//
//        user.setOrganization(org);
//    }
}