package storage.json_storage;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collection;
import java.util.List;

public abstract class JSONSerializer<T> {
    protected Gson gson;

    public JSONSerializer(ExclusionStrategy strategy) {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .addSerializationExclusionStrategy(strategy)
                .addDeserializationExclusionStrategy(strategy)
                .create();
    }

    public abstract void serialize(Collection<T> data, FileWriter writer);
    public abstract List<T> deserialize(FileReader reader);
}
