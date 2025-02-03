package com.expenses.resource;

import com.expenses.constants.ErrorConstants;
import com.expenses.dto.TransactionDTO;
import com.expenses.exception.ApplicationException;
import com.expenses.exception.ErrorResponse;
import com.expenses.service.TransactionService;
import com.expenses.util.CheckAuthentication;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/transactions")
@CheckAuthentication
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {

    @Inject
    TransactionService transactionService;

    @GET
    @Path("/year/{email}/{year}")
    public Response getTransactionsByYear(@PathParam("email") String email, @PathParam("year") int year) {
        try {
            List<TransactionDTO> transactions = transactionService.getTransactionsByYear(email, year);
            return Response.ok(transactions).build();
        } catch (ApplicationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getErrorCode(), e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE))
                    .build();
        }
    }

    @GET
    @Path("/month/{email}/{year}/{month}")
    public Response getTransactionsByMonth(@PathParam("email") String email, @PathParam("year") int year, @PathParam("month") int month) {
        try {
            List<TransactionDTO> transactions = transactionService.getTransactionsByMonth(email, year, month);
            return Response.ok(transactions).build();
        } catch (ApplicationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getErrorCode(), e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE))
                    .build();
        }
    }

    @GET
    @Path("/all/{email}")
    public Response getAllTransactions(@PathParam("email") String email) {
        try {
            List<TransactionDTO> transactions = transactionService.getAllTransactions(email);
            return Response.ok(transactions).build();
        } catch (ApplicationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getErrorCode(), e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE))
                    .build();
        }
    }
}