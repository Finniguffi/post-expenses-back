package com.expenses.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.Set;
import java.util.HashSet;

import com.expenses.exception.ApplicationExceptionMapper;
import com.expenses.interceptor.AuthorizationInterceptor;
import com.expenses.interceptor.CheckAuthenticationInterceptor;
import com.expenses.resource.AuthResource;
import com.expenses.resource.BalanceResource;
import com.expenses.resource.RecurringExpenseResource;
import com.expenses.resource.TransactionResource;
import com.expenses.resource.UserResource;
import com.expenses.resource.CategoryResource;

@ApplicationPath("/api")
public class JAXRSConfiguration extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(CheckAuthenticationInterceptor.class);
        resources.add(AuthorizationInterceptor.class);
        resources.add(AuthResource.class);
        resources.add(UserResource.class);
        resources.add(BalanceResource.class);
        resources.add(TransactionResource.class);
        resources.add(RecurringExpenseResource.class);
        resources.add(CategoryResource.class);
        resources.add(ApplicationExceptionMapper.class);
        return resources;
    }
}