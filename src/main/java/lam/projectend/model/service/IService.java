package lam.projectend.model.service;

import java.util.List;
import java.util.Optional;

public interface IService<T,E> {
    List<T> findAll();

    Optional<T> findById(E id);

    T save(T t);

    void remove(Long id);
}
