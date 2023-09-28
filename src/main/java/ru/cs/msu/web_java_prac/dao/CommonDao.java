package ru.cs.msu.web_java_prac.dao;

import ru.cs.msu.web_java_prac.entities.Common;
import java.util.Collection;

public interface CommonDao<T extends Common<ID>, ID> {
    T getById(ID id);

    Collection<T> getAll();

    void save(T entity);

    void saveCollection(Collection<T> entities);

    void delete(T entity);

    void deleteById(ID id);

    void update(T entity);
}