package api.vm_category;

import domain.vm_category.CategoryService;
import domain.vm_category.CategoryStorage;
import domain.vm_category.VMCategory;

import java.util.List;
import java.util.Optional;

import static spark.Spark.halt;

public class CategoryRESTService implements CategoryService {
    private CategoryStorage storage;

    public CategoryRESTService(CategoryStorage storage) {
        this.storage = storage;
    }

    @Override
    public List<VMCategory> getAll() {
        return (List<VMCategory>) storage.findAll();
    }

    @Override
    public VMCategory getSingle(String name) {
        Optional<VMCategory> user = storage.findByName(name);
        if (!user.isPresent())
            halt(404, "Category with the name "
                    + name + " not found");

        return user.get();
    }

    @Override
    public void post(VMCategory category) {
        if (!storage.add(category))
            halt(409, "Category with the name "
                    + category.getName() + " already exists");
    }

    @Override
    public void put(String name, VMCategory category) {
        if (!storage.update(name, category))
            halt(404, "User with the name "
                    + name + " not found");
    }

    @Override
    public void delete(String name) {
        if (!storage.delete(name))
            halt(404, "User with the name "
                    + name + " not found");
    }
}
