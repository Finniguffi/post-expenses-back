package com.expenses.interceptor;

import com.expenses.constants.ErrorConstants;
import com.expenses.exception.ApplicationException;
import com.expenses.exception.ErrorResponse;
import com.expenses.util.Jwt;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Provider
public class AuthorizationInterceptor implements ContainerRequestFilter {

    private static final List<String> EXCLUDED_PATHS = Arrays.asList("/auth/login", "/auth/register");

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();

        if (EXCLUDED_PATHS.contains(path)) {
            System.err.println("TESTE1");
            return;
        }

        String authorizationHeader = requestContext.getHeaderString("Authorization");

        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponse(ErrorConstants.AUTHORIZATION_HEADER_MISSING_CODE, ErrorConstants.AUTHORIZATION_HEADER_MISSING_MESSAGE))
                    .build());
            return;
        }

        String token = authorizationHeader.trim();

        try {
            String userEmail = Jwt.validateTokenAndGetEmail(token);
            if (userEmail == null) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity(new ErrorResponse(ErrorConstants.INVALID_TOKEN_CODE, ErrorConstants.INVALID_TOKEN_MESSAGE))
                        .build());
                return;
            }

            SecurityContext originalContext = requestContext.getSecurityContext();
            SecurityContext securityContext = new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return () -> userEmail;
                }

                @Override
                public boolean isUserInRole(String role) {
                    return originalContext.isUserInRole(role);
                }

                @Override
                public boolean isSecure() {
                    return originalContext.isSecure();
                }

                @Override
                public String getAuthenticationScheme() {
                    return "Bearer";
                }
            };
            requestContext.setSecurityContext(securityContext);
        } catch (ApplicationException e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponse(e.getErrorCode(), e.getMessage()))
                    .build());
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE))
                    .build());
        }
    }
}