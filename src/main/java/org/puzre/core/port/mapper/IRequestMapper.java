package org.puzre.core.port.mapper;

public interface IRequestMapper <D, REQ> {

    D toDomain(REQ requestDto);

}
