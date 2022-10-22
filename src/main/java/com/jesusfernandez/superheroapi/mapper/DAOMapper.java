package com.jesusfernandez.superheroapi.mapper;

import java.util.List;
import java.util.Set;

public interface DAOMapper<D, E> {

    E toDao(D dto);

    D toDto(E entity);

    List<E> toDao(List<D> dtoList);

    List<D> toDto(List<E> entityList);

    Set<E> toDao(Set<D> dtoList);

    Set<D> toDto(Set<E> entityList);

}
