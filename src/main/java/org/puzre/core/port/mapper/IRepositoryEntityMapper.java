package org.puzre.core.port.mapper;

public interface IRepositoryEntityMapper <D, E>{

    E toEntity(D domain);

}
