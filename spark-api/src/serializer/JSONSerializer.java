package serializer;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collection;
import java.util.List;

public abstract class JSONSerializer<T> {
    protected Gson gson;

    public abstract void serialize(Collection<T> data, FileWriter writer);
    public abstract List<T> deserialize(FileReader reader);
}
