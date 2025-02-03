package com.expenses.config;

import com.expenses.job.RecurringExpenseJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.context.Initialized;

@ApplicationScoped
public class QuartzConfig {

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            JobDetail job = JobBuilder.newJob(RecurringExpenseJob.class)
                    .withIdentity("recurringExpenseJob", "group1")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("recurringExpenseTrigger", "group1")
                    .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
                    .build();

            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}