package domain.virtual_machine;


import java.util.List;
import java.util.Optional;

public interface VirtualMachineStorage {
    Iterable<VirtualMachine> findAll();
    Optional<VirtualMachine> findByName(String name);
    boolean add(VirtualMachine entity);
    boolean update(String name, VirtualMachine entity);
    boolean delete(String name);
}