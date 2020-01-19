package storage.json_storage.drive.serializing;

import com.google.gson.ExclusionStrategy;
import com.google.gson.GsonBuilder;
import application.App;
import domain.drive.Drive;
import domain.virtual_machine.VirtualMachine;
import storage.json_storage.JSONSerializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DriveSerializer extends JSONSerializer<Drive> {
    public DriveSerializer(ExclusionStrategy strategy) {
        super(strategy);
    }

    @Override
    public void serialize(Collection<Drive> data, FileWriter writer) {
        gson.toJson(data, writer);
    }

    @Override
    public List<Drive> deserialize(FileReader reader) {
        return Arrays.asList(gson.fromJson(reader, Drive[].class));
    }
}
