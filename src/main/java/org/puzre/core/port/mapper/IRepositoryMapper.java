package org.puzre.core.port.mapper;

public interface IRepositoryMapper<D, E> {

    E toEntity(D domain);

}
