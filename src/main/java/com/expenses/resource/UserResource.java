package com.expenses.resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.expenses.entity.UserEntity;
import com.expenses.service.AuthService;
import com.expenses.service.BalanceService;
import com.expenses.service.UserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/user")
public class UserResource {
    
    @Inject
    AuthService authService;

    @Inject
    UserService userService;

    @Inject
    BalanceService balanceService;

    private static final Logger logger = LoggerFactory.getLogger(AuthResource.class);

    @GET
    @Path("{email}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getUserByEmail(@PathParam("email") String email) {
        logger.info("Fetching user with email: {}", email);
        UserEntity user = userService.getUserByEmail(email);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

    @GET
    @Path("/balance/{email}")
    @Produces("application/json")
    public Response getUserBalance(@PathParam("email") String email) {
        Double balance = balanceService.getBalance(email);
        if (balance != null) {
            return Response.ok().entity("User balance: " + balance).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found or balance not available").build();
        }
    }
}
