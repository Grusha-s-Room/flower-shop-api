package org.puzre.core.port.mapper;

public interface IRepositoryDomainMapper <D, E> {

    D toDomain(E entity);

}
