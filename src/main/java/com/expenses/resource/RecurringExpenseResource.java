package com.expenses.resource;

import com.expenses.constants.ErrorConstants;
import com.expenses.dto.TransactionDTO;
import com.expenses.entity.RecurringExpenseEntity;
import com.expenses.exception.ApplicationException;
import com.expenses.exception.ErrorResponse;
import com.expenses.service.RecurringExpenseService;
import com.expenses.util.CheckAuthentication;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
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
    @Path("/create/{dayOfMonth}")
    public Response createRecurringExpense(@PathParam("dayOfMonth") int dayOfMonth, TransactionDTO transactionDTO) {
        try {
            recurringExpenseService.createRecurringExpense(transactionDTO, dayOfMonth);
            return Response.ok("Recurring expense created successfully").build();
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
    @CheckAuthentication
    public Response getRecurringExpenses(@PathParam("email") String email) {
        try {
            List<RecurringExpenseEntity> recurringExpenses = recurringExpenseService.getRecurringExpenses(email);
            return Response.ok(recurringExpenses).build();
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
    @Path("/active/{email}")
    @CheckAuthentication
    public Response getActiveRecurringExpenses(@PathParam("email") String email) {
        try {
            List<RecurringExpenseEntity> activeRecurringExpenses = recurringExpenseService.getActiveRecurringExpenses(email);
            return Response.ok(activeRecurringExpenses).build();
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

    @PUT
    @Path("/disable/{id}")
    public Response disableRecurringExpense(@PathParam("id") Long id) {
        try {
            recurringExpenseService.disableRecurringExpense(id);
            return Response.ok("Recurring expense disabled successfully").build();
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