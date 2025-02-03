package com.expenses.resource;

import com.expenses.dto.TransactionDTO;
import com.expenses.entity.RecurringExpenseEntity;
import com.expenses.service.RecurringExpenseService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/recurring-expenses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecurringExpenseResource {

    @Inject
    RecurringExpenseService recurringExpenseService;

    @POST
    @Path("/create")
    public Response createRecurringExpense(@PathParam("email") String email, @PathParam("dayOfMonth") int dayOfMonth, TransactionDTO transactionDTO) {
        try {
            recurringExpenseService.createRecurringExpense(email, transactionDTO.getAmount(), transactionDTO.getDescription(), dayOfMonth);
            return Response.ok("Recurring expense created successfully").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/all/{email}")
    public Response getRecurringExpenses(@PathParam("email") String email) {
        List<RecurringExpenseEntity> recurringExpenses = recurringExpenseService.getRecurringExpenses(email);
        return Response.ok(recurringExpenses).build();
    }

    @GET
    @Path("/active/{email}")
    public Response getActiveRecurringExpenses(@PathParam("email") String email) {
        List<RecurringExpenseEntity> activeRecurringExpenses = recurringExpenseService.getActiveRecurringExpenses(email);
        return Response.ok(activeRecurringExpenses).build();
    }

    @PUT
    @Path("/disable/{id}")
    public Response disableRecurringExpense(@PathParam("id") Long id) {
        recurringExpenseService.disableRecurringExpense(id);
        return Response.ok("Recurring expense disabled successfully").build();
    }
}