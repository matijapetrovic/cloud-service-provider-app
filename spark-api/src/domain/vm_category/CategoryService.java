package domain.vm_category;

import java.util.List;

public interface CategoryService {
    List<VMCategory> getAll();
    VMCategory getSingle(String name);
    void post(VMCategory category);
    void put(String name, VMCategory category);
    void delete(String name);
}

