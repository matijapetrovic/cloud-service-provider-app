package services;

import models.Drive;
import models.VMCategory;
import repositories.CategoryRepository;
import repositories.DriveRepository;

public class CategoryService extends Service<String, VMCategory>{
    public CategoryService() {
        super(new CategoryRepository("./data/categories.json"));
    }
}
