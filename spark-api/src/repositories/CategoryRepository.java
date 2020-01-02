package repositories;

import models.VMCategory;
import serializer.category.CategorySerializer;

public class CategoryRepository extends JSONFileRepository<String, VMCategory>{
    public CategoryRepository(String filePath) {
        super(new CategorySerializer(), filePath);
    }
}
