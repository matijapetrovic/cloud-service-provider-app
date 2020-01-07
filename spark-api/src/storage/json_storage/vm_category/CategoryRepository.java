package storage.json_storage.vm_category;

import domain.vm_category.VMCategory;
import storage.json_storage.JSONFileRepository;
import storage.json_storage.vm_category.serializing.CategorySerializer;

public class CategoryRepository extends JSONFileRepository<String, VMCategory> {
    public CategoryRepository(String filePath) {
        super(new CategorySerializer(), filePath);
    }
}
