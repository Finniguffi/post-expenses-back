package com.expenses.resource;

import com.expenses.dto.TransactionDTO;
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
        List<TransactionDTO> transactions = transactionService.getTransactionsByYear(email, year);
        return Response.ok(transactions).build();
    }

    @GET
    @Path("/month/{email}/{year}/{month}")
    public Response getTransactionsByMonth(@PathParam("email") String email, @PathParam("year") int year, @PathParam("month") int month) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByMonth(email, year, month);
        return Response.ok(transactions).build();
    }

    @GET
    @Path("/all/{email}")
    public Response getAllTransactions(@PathParam("email") String email) {
        List<TransactionDTO> transactions = transactionService.getAllTransactions(email);
        return Response.ok(transactions).build();
    }
}