package storage.json_storage.vm_category.serializing;

import com.google.gson.ExclusionStrategy;
import com.google.gson.GsonBuilder;

import domain.vm_category.VMCategory;
import storage.json_storage.JSONSerializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CategorySerializer extends JSONSerializer<VMCategory> {
    public CategorySerializer(ExclusionStrategy strategy) {
        super(strategy);
    }

    @Override
    public void serialize(Collection<VMCategory> data, FileWriter writer) {
        gson.toJson(data, writer);
    }

    @Override
    public List<VMCategory> deserialize(FileReader reader) {
        return Arrays.asList(gson.fromJson(reader, VMCategory[].class));
    }

}
