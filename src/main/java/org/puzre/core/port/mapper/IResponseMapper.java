package org.puzre.core.port.mapper;

public interface IResponseMapper <D, RES>{

    RES toResponseDto(D domain);

}
