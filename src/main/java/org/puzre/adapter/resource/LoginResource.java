package org.puzre.adapter.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import org.puzre.adapter.resource.dto.request.LoginUserRequestDto;
import org.puzre.adapter.resource.dto.response.LoginUserResponseDto;
import org.puzre.core.domain.Cookie;
import org.puzre.core.domain.LoginUser;
import org.puzre.core.port.mapper.IResourceMapper;
import org.puzre.core.port.mapper.IResponseMapper;
import org.puzre.core.port.service.ILoginService;

@Path("flower-shop")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    private final IResourceMapper<LoginUser, LoginUserRequestDto, LoginUserResponseDto> iLoginUserMapper;
    private final IResponseMapper<Cookie, NewCookie> iCookieMapper;

    private final ILoginService iLoginService;

    public LoginResource(
            IResourceMapper<LoginUser, LoginUserRequestDto, LoginUserResponseDto> iLoginUserMapper,
            IResponseMapper<Cookie, NewCookie> iCookieMapper,
            ILoginService iLoginService
    ){
        this.iLoginUserMapper = iLoginUserMapper;
        this.iCookieMapper = iCookieMapper;
        this.iLoginService = iLoginService;
    }

    @Path("/login")
    @POST
    @PermitAll
    public Response login(LoginUserRequestDto loginUserRequestDto) {
        LoginUser loginUserInput = iLoginUserMapper.toDomain(loginUserRequestDto);
        LoginUser loginUserOutput = iLoginService.login(loginUserInput);
        LoginUserResponseDto loginUserResponseDto = iLoginUserMapper.toResponseDto(loginUserOutput);
        NewCookie cookie = iCookieMapper.toResponseDto(loginUserOutput.getCookie());
        return Response.ok().entity(loginUserResponseDto).cookie(cookie).build();
    }

}
