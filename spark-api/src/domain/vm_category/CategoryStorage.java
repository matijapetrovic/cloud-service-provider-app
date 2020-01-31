package domain.vm_category;

import java.util.Optional;

public interface CategoryStorage {
    Iterable<VMCategory> findAll();
    Optional<VMCategory> findByName(String name);
    boolean add(VMCategory entity);
    boolean update(String name, VMCategory entity);
    boolean delete(String name);
}
