package com.expenses.constants;

public final class ErrorConstants {

    private ErrorConstants() {
        // Private constructor to prevent instantiation
    }

    // User-related errors
    public static final String USER_NOT_FOUND_CODE = "ERR001";
    public static final String USER_NOT_FOUND_MESSAGE = "User not found";

    // Recurring expense-related errors
    public static final String RECURRING_EXPENSE_NOT_FOUND_CODE = "ERR002";
    public static final String RECURRING_EXPENSE_NOT_FOUND_MESSAGE = "Recurring expense not found";

    // Scheduler-related errors
    public static final String SCHEDULER_ERROR_CODE = "ERR003";
    public static final String SCHEDULER_ERROR_MESSAGE = "Failed to schedule job";

    // Authorization-related errors
    public static final String AUTHORIZATION_HEADER_MISSING_CODE = "ERR004";
    public static final String AUTHORIZATION_HEADER_MISSING_MESSAGE = "Authorization header is missing";

    public static final String INVALID_TOKEN_CODE = "ERR005";
    public static final String INVALID_TOKEN_MESSAGE = "Invalid token";

    public static final String UNAUTHORIZED_ACCESS_CODE = "ERR006";
    public static final String UNAUTHORIZED_ACCESS_MESSAGE = "You are not authorized to access this resource";

    // Balance-related errors
    public static final String INSUFFICIENT_BALANCE_CODE = "ERR007";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient balance";

    public static final String INVALID_AMOUNT_CODE = "ERR008";
    public static final String INVALID_AMOUNT_MESSAGE = "Amount must be greater than zero";

    // General errors
    public static final String INTERNAL_SERVER_ERROR_CODE = "ERR009";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "An internal server error occurred";
}