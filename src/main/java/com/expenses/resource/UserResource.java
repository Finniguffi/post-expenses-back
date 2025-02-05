package com.expenses.resource;

import com.expenses.constants.ErrorConstants;
import com.expenses.dto.UserDTO;
import com.expenses.exception.ApplicationException;
import com.expenses.exception.ErrorResponse;
import com.expenses.service.AuthService;
import com.expenses.service.BalanceService;
import com.expenses.service.UserService;
import com.expenses.util.CheckAuthentication;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    AuthService authService;

    @Inject
    UserService userService;

    @Inject
    BalanceService balanceService;

    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

    @GET
    @Path("{email}")
    @CheckAuthentication
    public Response getUserByEmail(@PathParam("email") String email) {
        try {
            logger.info("Fetching user with email: {}", email);
            UserDTO user = userService.getUserByEmail(email);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse(ErrorConstants.USER_NOT_FOUND_CODE, ErrorConstants.USER_NOT_FOUND_MESSAGE))
                        .build();
            }
            return Response.ok(user).build();
        } catch (ApplicationException e) {
            logger.error("Error fetching user: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getErrorCode(), e.getMessage()))
                    .build();
        } catch (Exception e) {
            logger.error("Unexpected error fetching user", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE))
                    .build();
        }
    }

    @GET
    @Path("/balance/{email}")
    @CheckAuthentication
    public Response getUserBalance(@PathParam("email") String email) {
        try {
            Double balance = balanceService.getBalance(email);
            return Response.ok().entity("User balance: " + balance).build();
        } catch (ApplicationException e) {
            logger.error("Error fetching user balance: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getErrorCode(), e.getMessage()))
                    .build();
        } catch (Exception e) {
            logger.error("Unexpected error fetching user balance", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE))
                    .build();
        }
    }
}