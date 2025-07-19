package org.puzre.adapter.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.puzre.adapter.resource.dto.request.LoginUserRequestDto;
import org.puzre.adapter.resource.dto.response.LoginUserResponseDto;
import org.puzre.core.domain.LoginUser;
import org.puzre.core.port.mapper.IResourceMapper;
import org.puzre.core.port.service.ILoginService;

@Path("flower-shop")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    private final IResourceMapper<LoginUser, LoginUserRequestDto, LoginUserResponseDto> iLoginUserMapper;

    private final ILoginService iLoginService;

    public LoginResource(
            IResourceMapper<LoginUser, LoginUserRequestDto, LoginUserResponseDto> iLoginUserMapper,
            ILoginService iLoginService
    ){
        this.iLoginUserMapper = iLoginUserMapper;
        this.iLoginService = iLoginService;
    }

    @Path("/login")
    @POST
    @PermitAll
    public Response login(LoginUserRequestDto loginUserRequestDto) {
        LoginUser loginUserInput = iLoginUserMapper.toDomain(loginUserRequestDto);
        LoginUser loginUserOutput = iLoginService.login(loginUserInput);
        LoginUserResponseDto loginUserResponseDto = iLoginUserMapper.toResponseDto(loginUserOutput);
        return Response.ok().entity(loginUserResponseDto).build();
    }

}
