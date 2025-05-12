package com.example.desafioBackend.service.serviceInterface;

import java.util.List;

public interface Service <T,ID>{
    T add(T entity);

    T delete(ID id);

    List<T> getAll();

    T getById (ID id);

}
