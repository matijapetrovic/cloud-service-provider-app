package storage.json_storage.vm_category.serializing;

import com.google.gson.GsonBuilder;

import domain.vm_category.VMCategory;
import storage.json_storage.JSONSerializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CategorySerializer extends JSONSerializer<VMCategory> {
    public CategorySerializer() {
        gson = new GsonBuilder()
                .addSerializationExclusionStrategy(
                        new CategoryExclusionStrategy())
                .addDeserializationExclusionStrategy(
                        new CategoryExclusionStrategy())
                .create();
    }

    @Override
    public void serialize(Collection<VMCategory> data, FileWriter writer) {
        gson.toJson(data, writer);
    }

    @Override
    public List<VMCategory> deserialize(FileReader reader) {
        List<VMCategory> data = Arrays.asList(gson.fromJson(reader, VMCategory[].class));
        return data;
    }

}
