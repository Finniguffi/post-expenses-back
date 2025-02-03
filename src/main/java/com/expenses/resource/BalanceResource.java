package com.expenses.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.expenses.dto.TransactionDTO;
import com.expenses.service.BalanceService;
import com.expenses.service.UserService;
import com.expenses.util.CheckAuthentication;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/balance")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CheckAuthentication
public class BalanceResource {

    @Inject
    BalanceService balanceService;

    @Inject
    UserService userService;

    @Context
    SecurityContext securityContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(BalanceResource.class);

    @GET
    @Path("/{email}")
    public Response balance(@PathParam("email") String email) {
        Double balance = balanceService.getBalance(email);
        if (balance != null) {
            return Response.ok().entity(balance).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to get balance").build();
        }
    }

    @POST
    @Path("/deposit/{email}/{amount}")
    public Response deposit(@PathParam("email") String email, @PathParam("amount") double amount) {
        Double newBalance = balanceService.postBalance(email, amount);
        if (newBalance != null) {
            return Response.ok().entity(newBalance).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to add money").build();
        }
    }

    @POST
    @Path("/expense/{email}")
    public Response postExpense(@PathParam("email") String email, TransactionDTO transactionDTO) {
        try {
            Double newBalance = balanceService.processExpense(email, transactionDTO);
            return Response.ok().entity(newBalance).build();
        } catch (IllegalArgumentException error) {
            return Response.status(Response.Status.BAD_REQUEST).entity(error.getMessage()).build();
        } catch (Exception error) {
            LOGGER.error("Error posting expense", error);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while processing the expense").build();
        }
    }
}