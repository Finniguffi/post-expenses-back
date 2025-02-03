package com.expenses.job;

import com.expenses.entity.RecurringExpenseEntity;
import com.expenses.entity.TransactionEntity;
import com.expenses.entity.UserEntity;
import com.expenses.repository.RecurringExpenseRepository;
import com.expenses.repository.TransactionRepository;
import com.expenses.repository.UserRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RecurringExpenseJobTest {

    @Mock
    private RecurringExpenseRepository recurringExpenseRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RecurringExpenseJob job;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testExecute() throws JobExecutionException {
        JobExecutionContext context = mock(JobExecutionContext.class);

        // Mock the recurring expenses
        RecurringExpenseEntity recurringExpense = new RecurringExpenseEntity();
        recurringExpense.setAmount(100.0);
        recurringExpense.setDescription("Monthly Subscription");
        recurringExpense.setDayOfMonth(LocalDateTime.now().getDayOfMonth());
        recurringExpense.setActive(true);
        recurringExpense.setUser(mock(UserEntity.class));

        List<RecurringExpenseEntity> recurringExpenses = Collections.singletonList(recurringExpense);
        
        // Explicitly cast the mock to PanacheQuery<RecurringExpenseEntity>
        PanacheQuery<RecurringExpenseEntity> query = mock(PanacheQuery.class);
        when(query.list()).thenReturn(recurringExpenses);
        
        // Ensure the repository returns the typed query
        when(recurringExpenseRepository.find("active", true)).thenReturn(query);

        // Execute the job
        job.execute(context);

        // Verify that a transaction was created
        verify(transactionRepository, times(1)).persist(any(TransactionEntity.class));
    }
}