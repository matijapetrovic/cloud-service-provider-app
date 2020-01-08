package storage.json_storage.virtual_machine.serializing;

import com.google.gson.GsonBuilder;
import domain.virtual_machine.VirtualMachine;
import storage.json_storage.JSONSerializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class VirtualMachineSerializer extends JSONSerializer<VirtualMachine> {

    public VirtualMachineSerializer() {
        gson = new GsonBuilder()
                .addSerializationExclusionStrategy(
                        new VirtualMachineExclusionStrategy())
                .addDeserializationExclusionStrategy(
                        new VirtualMachineExclusionStrategy())
                .create();
    }

    @Override
    public void serialize(Collection<VirtualMachine> data, FileWriter writer) {
        gson.toJson(data, writer);
    }

    @Override
    public List<VirtualMachine> deserialize(FileReader reader) {
        List<VirtualMachine> data = Arrays.asList(gson.fromJson(reader, VirtualMachine[].class));
        return data;
    }
}
