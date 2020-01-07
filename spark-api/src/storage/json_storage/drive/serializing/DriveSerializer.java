package storage.json_storage.drive.serializing;

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
    public DriveSerializer() {
        gson = new GsonBuilder()
                .addSerializationExclusionStrategy(
                        new DriveExclusionStrategy())
                .addDeserializationExclusionStrategy(
                        new DriveExclusionStrategy())
                .create();
    }

    @Override
    public void serialize(Collection<Drive> data, FileWriter writer) {
        gson.toJson(data, writer);
    }

    @Override
    public List<Drive> deserialize(FileReader reader) {
        List<Drive> data = Arrays.asList(gson.fromJson(reader, Drive[].class));
//        buildReferences(data);
        return data;
    }

//    private void buildReferences(List<Drive> data) {
//        data.forEach(this::buildReferences);
//    }
//
//    private void buildReferences(Drive drive) {
//        VirtualMachine vm  = App.vmService.findByKey(drive.getVm().getName()).orElse(null);
//        drive.setVm(vm);
//    }
}
