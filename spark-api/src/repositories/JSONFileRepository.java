package repositories;

import com.google.gson.stream.JsonReader;
import main.App;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public abstract class JSONFileRepository<ID, T> implements Repository<ID, T> {
    private String filePath;
    private Collection<T> repo;

    public JSONFileRepository(String filePath) {
        this.filePath = filePath;
        this.repo = new ArrayList<T>();
    }
    @Override
    public void save(T entity) {
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

    protected void loadEntities(Class<T> cls) {
        List<T> data;
        try (JsonReader reader = new JsonReader(new FileReader(filePath))) {
            data = Arrays.asList(App.g.fromJson(reader, cls));
            repo.addAll(data);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            App.logger.log(Level.WARNING, "Reading from " + filePath + " failed");
        }
    }

    private void saveEntities() {
        try (FileWriter writer = new FileWriter(filePath)) {
            App.g.toJson(repo, writer);
        } catch (IOException e) {
            App.logger.log(Level.WARNING, "Saving to " + filePath + " failed");
        }
    }

}
