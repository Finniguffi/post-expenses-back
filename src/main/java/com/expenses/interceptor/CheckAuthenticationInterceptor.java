package com.expenses.interceptor;

import com.expenses.constants.ErrorConstants;
import com.expenses.exception.ApplicationException;
import com.expenses.exception.ErrorResponse;
import com.expenses.util.CheckAuthentication;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.io.Serializable;

@CheckAuthentication
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class CheckAuthenticationInterceptor implements Serializable {

    @Inject
    private SecurityContext securityContext;

    @AroundInvoke
    public Object checkAuthentication(InvocationContext context) throws Exception {
        try {
            Object[] parameters = context.getParameters();
            String email = (String) parameters[0]; // The email must be the first parameter

            String authenticatedUserEmail = securityContext.getUserPrincipal().getName();
            if (!authenticatedUserEmail.equals(email)) {
                throw new ApplicationException(ErrorConstants.UNAUTHORIZED_ACCESS_CODE, ErrorConstants.UNAUTHORIZED_ACCESS_MESSAGE);
            }

            return context.proceed();
        } catch (ApplicationException e) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse(e.getErrorCode(), e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE))
                    .build();
        }
    }
}