package com.javafx_e2_thanhnn_c2306l.IGeneric;

import java.util.List;
import java.util.Optional;

public interface IService<T,ID> {
    void add(T t);
    void remove(T t);
    Optional<T> findByID(ID id);
    List<T> findAll();
    void edit(T t);

}
