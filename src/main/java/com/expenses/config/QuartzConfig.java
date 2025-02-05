package com.expenses.config;

import com.expenses.job.RecurringExpenseJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class QuartzConfig {

    public void init(@Observes StartupEvent event) {
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

            if (!scheduler.checkExists(job.getKey())) {
                scheduler.scheduleJob(job, trigger);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}