package com.zengjinhao.pos.config;

import com.zengjinhao.async.AsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * AsyncConfig
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2018/1/25
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    //SpringBoot中开启异步支持非常简单，只需要在配置类上面加上注解@EnableAsync，同时定义自己的线程池即可。
    // 也可以不定义自己的线程池，则使用系统默认的线程池。
    // 这个注解可以放在Application启动类上，但是更推荐放在配置类上面

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(100);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60 * 10);
        executor.setThreadNamePrefix("AsyncThread-");
        executor.initialize(); //如果不初始化，导致找到不到执行器
        return executor;
    }
    //实现AsyncConfigurer接口，也可以继承AsyncConfigurerSupport类来实现
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }
}
