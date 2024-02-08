package com.example.platform.service;

import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface Service<E, D> {
    List<E> findAll(PageRequest pq);
    E create(D d);
    E findById(Long id);
    E deleteById(Long id);
    E update(Long id, D d);
}
