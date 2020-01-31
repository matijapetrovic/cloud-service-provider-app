package storage.json_storage;

import application.App;
import domain.Model;
import storage.Repository;

import java.io.*;
import java.util.*;
import java.util.logging.Level;

public class JSONFileRepository<K, E extends Model<K>> implements Repository<K, E> {
    private JSONSerializer<E> serializer;
    private String filePath;

    List<E> data;

    public JSONFileRepository(JSONSerializer<E> serializer, String filePath) {
        this.serializer = serializer;
        this.filePath = filePath;
        data = new ArrayList<>();
        loadEntities();
    }

    private void loadEntities() {
        List<E> data;
        try (FileReader reader = new FileReader(filePath)) {
            data = serializer.deserialize(reader);
            this.data.clear();
            this.data.addAll(data);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            App.logger.log(Level.WARNING, "Reading from " + filePath + " failed");
        }
    }

    @Override
    public void save(E entity) {
        if (data.contains(entity)) {
            int idx = data.indexOf(entity);
            data.remove(entity);
            data.add(idx, entity);
        }
        else
            data.add(entity);
    }

    @Override
    public void delete(E entity) {
        data.remove(entity);
    }

    @Override
    public List<E> findAll() {
        return data;
    }

    @Override
    public Optional<E> findByKey(K key) {
        return findAll()
                .stream()
                .filter(x -> x.getKey().equals(key))
                .findFirst();
    }

    @Override
    public void saveChanges() {
        try (FileWriter writer = new FileWriter(filePath)) {
            serializer.serialize(data, writer);
        } catch (IOException e) {
            App.logger.log(Level.WARNING, "Saving to " + filePath + " failed");
        }
    }
}
