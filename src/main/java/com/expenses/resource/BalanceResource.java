package com.expenses.resource;

import com.expenses.constants.ErrorConstants;
import com.expenses.dto.TransactionDTO;
import com.expenses.exception.ApplicationException;
import com.expenses.exception.ErrorResponse;
import com.expenses.service.BalanceService;
import com.expenses.service.UserService;
import com.expenses.util.CheckAuthentication;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        try {
            Double balance = balanceService.getBalance(email);
            return Response.ok().entity(balance).build();
        } catch (ApplicationException e) {
            LOGGER.error("Error getting balance: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getErrorCode(), e.getMessage()))
                    .build();
        } catch (Exception e) {
            LOGGER.error("Unexpected error getting balance", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE))
                    .build();
        }
    }

    @POST
    @Path("/deposit/{email}")
    public Response deposit(@PathParam("email") String email, TransactionDTO transactionDTO) {
        try {
            Double newBalance = balanceService.processDeposit(email, transactionDTO);
            return Response.ok().entity(newBalance).build();
        } catch (ApplicationException e) {
            LOGGER.error("Error during deposit: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getErrorCode(), e.getMessage()))
                    .build();
        } catch (Exception e) {
            LOGGER.error("Unexpected error during deposit", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE))
                    .build();
        }
    }

    @POST
    @Path("/expense/{email}")
    public Response postExpense(@PathParam("email") String email, TransactionDTO transactionDTO) {
        try {
            Double newBalance = balanceService.processExpense(email, transactionDTO);
            return Response.ok().entity(newBalance).build();
        } catch (ApplicationException e) {
            LOGGER.error("Error posting expense: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getErrorCode(), e.getMessage()))
                    .build();
        } catch (Exception e) {
            LOGGER.error("Unexpected error posting expense", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE))
                    .build();
        }
    }
}