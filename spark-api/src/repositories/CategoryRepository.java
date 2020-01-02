package repositories;

import models.Drive;
import models.VMCategory;
import serializer.category.CategorySerializer;
import serializer.drive.DriveSerializer;

public class CategoryRepository extends JSONFileRepository<String, VMCategory>{
    public CategoryRepository(String filePath) {
        super(new CategorySerializer(), filePath);
    }
}
