package com.epicode.models.repositories;

import java.util.List;

// Parent CRUD Repository. Can be implemented by any other repository that implement it 
public interface AppRepository<T> {
    List<T> findAll();
    T findById(Long id);
    T save(T entity);
    T update(T entity, Long id);
    boolean deleteById(Long id);
    boolean deleteAll();
}
