package com.expenses.resource;

import com.expenses.constants.ErrorConstants;
import com.expenses.dto.LoginDTO;
import com.expenses.dto.RegisterDTO;
import com.expenses.exception.ApplicationException;
import com.expenses.exception.ErrorResponse;
import com.expenses.service.AuthService;
import com.expenses.util.Jwt;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @Inject
    Jwt jwt;

    private static final Logger logger = LoggerFactory.getLogger(AuthResource.class);

    @POST
    @Path("/login")
    public Response login(LoginDTO loginDTO) {
        try {
            String token = authService.authenticate(loginDTO);
            return Response.ok().entity(token).build();
        } catch (ApplicationException e) {
            logger.error("Error during login: {}", e.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponse(e.getErrorCode(), e.getMessage()))
                    .build();
        } catch (Exception e) {
            logger.error("Unexpected error during login", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE))
                    .build();
        }
    }

    @POST
    @Path("/register")
    public Response register(RegisterDTO registerDTO) {
        try {
            logger.info("Registering user with email: {}", registerDTO.getEmail());
            authService.register(registerDTO);
            return Response.status(Response.Status.CREATED).build();
        } catch (ApplicationException e) {
            logger.error("Error during registration: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getErrorCode(), e.getMessage()))
                    .build();
        } catch (Exception e) {
            logger.error("Unexpected error during registration", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE))
                    .build();
        }
    }

    @GET
    @Path("/validate")
    public Response validateToken(@HeaderParam("Authorization") String token) {
        try {
            boolean isValid = jwt.validateToken(token);
            return Response.ok(isValid).build();
        } catch (ApplicationException e) {
            logger.error("Error during token validation: {}", e.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponse(e.getErrorCode(), e.getMessage()))
                    .build();
        } catch (Exception e) {
            logger.error("Unexpected error during token validation", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE))
                    .build();
        }
    }
}