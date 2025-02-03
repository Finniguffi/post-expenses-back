package com.expenses.interceptor;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;
import com.expenses.util.Jwt;

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
            return;
        }

        String authorizationHeader = requestContext.getHeaderString("Authorization");

        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        String token = authorizationHeader.trim();

        try {
            String userEmail = Jwt.validateTokenAndGetEmail(token);
            if (userEmail == null) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
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
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}