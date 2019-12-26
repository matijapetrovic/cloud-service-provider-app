package serializer.virtualmachine;

import com.google.gson.GsonBuilder;
import models.VirtualMachine;
import serializer.JSONSerializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class VMSerializer extends JSONSerializer<VirtualMachine> {

    public VMSerializer() {
        gson = new GsonBuilder()
                .addSerializationExclusionStrategy(
                        new VMExclusionStrategy())
                .addDeserializationExclusionStrategy(
                        new VMExclusionStrategy())
                .create();
    }

    @Override
    public void serialize(Collection<VirtualMachine> data, FileWriter writer) {
        gson.toJson(data, writer);
    }

    @Override
    public List<VirtualMachine> deserialize(FileReader reader) {
        List<VirtualMachine> data = Arrays.asList(gson.fromJson(reader, VirtualMachine[].class));
        buildReferences(data);
        return data;
    }

    private void buildReferences(List<VirtualMachine> data) {
        data.forEach(this::buildReferences);
    }

    private void buildReferences(VirtualMachine vm) {
//      buildDriveReferences(vm);
    }

//    private void buildDriveReferences(VirtualMachine vm) {
//        List<Drive> drives = new ArrayList<Drive>();
//        vm.getDrives()
//                .forEach(x -> App.driveService
//                        .findByKey(x.getKey()))
//                        .ifPresent(drives::add);
//        vm.setDrives(drives);
//    }
}
