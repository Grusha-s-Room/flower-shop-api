package org.puzre.adapter.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.puzre.adapter.resource.dto.request.SignUpUserRequestDto;
import org.puzre.core.domain.User;
import org.puzre.core.port.mapper.IRequestMapper;
import org.puzre.core.port.service.ISignUpService;

@Path("flower-shop")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SignUpResource {

    private final IRequestMapper<User, SignUpUserRequestDto> iRequestMapper;

    private final ISignUpService iSignUpService;

    public SignUpResource(IRequestMapper<User, SignUpUserRequestDto> iRequestMapper,
                          ISignUpService iSignUpService) {
        this.iRequestMapper = iRequestMapper;
        this.iSignUpService = iSignUpService;
    }

    @Path("/sign-up")
    @POST
    public Response signUp(SignUpUserRequestDto signUpUserRequestDto) {
        User user = iRequestMapper.toDomain(signUpUserRequestDto);
        iSignUpService.signUp(user);
        return Response.status(Response.Status.CREATED).build();
    }

}
