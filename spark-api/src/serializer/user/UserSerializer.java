package serializer.user;

import com.google.gson.GsonBuilder;
import models.Organization;
import models.User;
import serializer.JSONSerializer;
import serializer.organization.OrganizationExclusionStrategy;

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
        buildReferences(data);
        return data;
    }

    private void buildReferences(List<User> data) {
        data.forEach(this::buildReferences);
    }
    private void buildReferences(User org) {
        // TODO resi
    }
}
