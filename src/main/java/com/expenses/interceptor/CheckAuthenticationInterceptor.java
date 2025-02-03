package com.expenses.interceptor;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.io.Serializable;

import com.expenses.util.CheckAuthentication;

@CheckAuthentication
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class CheckAuthenticationInterceptor implements Serializable {

    @Inject
    private SecurityContext securityContext;

    @AroundInvoke
    public Object checkAuthentication(InvocationContext context) throws Exception {
        Object[] parameters = context.getParameters();
        String email = (String) parameters[0]; // The email must be the first parameter

        String authenticatedUserEmail = securityContext.getUserPrincipal().getName();
        if (!authenticatedUserEmail.equals(email)) {
            return Response.status(Response.Status.FORBIDDEN).entity("You are not authorized to deposit into this account").build();
        }

        return context.proceed();
    }
}