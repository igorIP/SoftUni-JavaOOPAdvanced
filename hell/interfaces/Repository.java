package hell.interfaces;

import java.util.List;
import java.util.Map;

public interface Repository<T> {
    void add(T element);

    List<T> findAll();

    T findByName(String name);
}
