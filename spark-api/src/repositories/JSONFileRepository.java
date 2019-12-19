package repositories;

import main.App;
import serializer.JSONSerializer;

import java.io.*;
import java.util.*;
import java.util.logging.Level;

public abstract class JSONFileRepository<ID, T> implements Repository<ID, T> {
    private String filePath;
    private Collection<T> repo;
    protected JSONSerializer serializer;

    public JSONFileRepository(String filePath) {
        this.filePath = filePath;
        repo = new ArrayList<T>();
        setSerializer();
        //loadEntities();
    }

    protected abstract void setSerializer();

    @Override
    public void save(T entity) {
        if (repo.contains(entity))
            repo.remove(entity);
        repo.add(entity);
        saveEntities();
    }

    @Override
    public void delete(T entity) {
        repo.remove(entity);
        saveEntities();
    }

    @Override
    public Collection<T> findAll() {
        return repo;
    }

    private void loadEntities() {
        List<T> data;
        try (FileReader reader = new FileReader(filePath)) {
            //data = Arrays.asList(App.g.fromJson(reader, cls));
            data = serializer.deserialize(reader);
            repo.addAll(data);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            App.logger.log(Level.WARNING, "Reading from " + filePath + " failed");
        }
    }

    private void saveEntities() {
        try (FileWriter writer = new FileWriter(filePath)) {
            //App.g.toJson(repo, writer);
            serializer.serialize(repo, writer);
        } catch (IOException e) {
            App.logger.log(Level.WARNING, "Saving to " + filePath + " failed");
        }
    }

}
