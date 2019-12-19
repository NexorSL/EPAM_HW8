package ua.epam.repository;

import java.util.Map;

public interface GenericRepository<T, ID> {
    boolean create(T entity);
    T getById(ID id);
    boolean update(T entity, ID id);
    boolean delete(ID id);
    Map<ID, T> getAll();
}
