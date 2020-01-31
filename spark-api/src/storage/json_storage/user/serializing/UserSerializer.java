package storage.json_storage.user.serializing;

import com.google.gson.ExclusionStrategy;
import com.google.gson.GsonBuilder;

import domain.user.User;
import storage.json_storage.JSONSerializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UserSerializer extends JSONSerializer<User> {

    public UserSerializer(ExclusionStrategy strategy) {
        super(strategy);
    }

    @Override
    public void serialize(Collection<User> data, FileWriter writer) {
        gson.toJson(data, writer);
    }

    @Override
    public List<User> deserialize(FileReader reader) {
        return Arrays.asList(gson.fromJson(reader, User[].class));
    }
}
