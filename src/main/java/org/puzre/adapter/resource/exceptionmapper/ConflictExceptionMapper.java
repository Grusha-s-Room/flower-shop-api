package org.puzre.adapter.resource.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.puzre.adapter.resource.dto.response.ErrorResponseDto;
import org.puzre.core.exception.ConflictException;

@Provider
public class ConflictExceptionMapper implements ExceptionMapper<ConflictException> {
    @Override
    public Response toResponse(ConflictException e) {
        return Response.status(Response.Status.CONFLICT).entity(new ErrorResponseDto(e.getMessage())).build();
    }
}
