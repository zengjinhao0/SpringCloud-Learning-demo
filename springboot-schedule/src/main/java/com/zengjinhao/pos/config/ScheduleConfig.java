package com.zengjinhao.pos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ScheduleConfig
 */
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    //有多个耗时任务，最好使用线程池来执行，添加一个配置类专门用来配置定时任务执行的线程池：
    @Bean(destroyMethod="shutdown")
    public ExecutorService taskExecutor() {
        return Executors.newScheduledThreadPool(5);
    }
}
