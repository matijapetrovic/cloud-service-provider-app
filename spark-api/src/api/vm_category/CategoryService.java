package api.vm_category;

import api.Service;
import domain.vm_category.VMCategory;
import storage.json_storage.vm_category.CategoryRepository;

public class CategoryService extends Service<String, VMCategory> {
    public CategoryService() {
        super(new CategoryRepository("./data/categories.json"));
    }
}
