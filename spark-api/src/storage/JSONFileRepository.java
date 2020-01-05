package storage;

import application.App;
import domain.Model;

import java.io.*;
import java.util.*;
import java.util.logging.Level;

public class JSONFileRepository<K, E extends Model<K>> implements Repository<K, E> {
    private JSONSerializer<E> serializer;
    private String filePath;

    private List<E> repo;

    public JSONFileRepository(JSONSerializer<E> serializer, String filePath) {
        this.serializer = serializer;
        this.filePath = filePath;
        repo = new ArrayList<E>();
        loadEntities();
    }

    @Override
    public void save(E entity) {
        if (repo.contains(entity)) {
            int idx = repo.indexOf(entity);
            repo.remove(entity);
            repo.add(idx, entity);
        }
        else
            repo.add(entity);
        saveChanges();
    }

    @Override
    public void delete(E entity) {
        repo.remove(entity);
        saveChanges();
    }

    @Override
    public List<E> findAll() {
        return repo;
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
            //App.g.toJson(repo, writer);
            serializer.serialize(repo, writer);
        } catch (IOException e) {
            App.logger.log(Level.WARNING, "Saving to " + filePath + " failed");
        }
    }

    private void loadEntities() {
        List<E> data;
        try (FileReader reader = new FileReader(filePath)) {
            //data = Arrays.asList(App.g.fromJson(reader, cls));
            data = serializer.deserialize(reader);
            repo.addAll(data);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            App.logger.log(Level.WARNING, "Reading from " + filePath + " failed");
        }
    }
}
