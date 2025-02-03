package com.expenses.resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.expenses.dto.LoginDTO;
import com.expenses.dto.RegisterDTO;
import com.expenses.service.AuthService;
import com.expenses.util.Jwt;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(AuthResource.class);
    
    @POST
    @Path("/login")
    public Response login(LoginDTO loginDTO) {
        String token = authService.authenticate(loginDTO);
        return Response.ok().entity(token).build();
    }

    @POST
    @Path("/register")
    public Response register(RegisterDTO registerDTO) {
        logger.info("Registering user with email: " + registerDTO.getEmail());
        authService.register(registerDTO);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/validate")
    public Response validateToken(@HeaderParam("Authorization") String token) {
        boolean isValid = Jwt.validateToken(token);
        return Response.ok(isValid).build();
    }
}
