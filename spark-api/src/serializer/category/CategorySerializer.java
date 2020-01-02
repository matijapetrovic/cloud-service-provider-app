package serializer.category;

import com.google.gson.GsonBuilder;

import models.VMCategory;
import serializer.JSONSerializer;

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
        buildReferences(data);
        return data;
    }

    private void buildReferences(List<VMCategory> data) {
        data.forEach(this::buildReferences);
    }

    private void buildReferences(VMCategory org) {

    }
}
