package org.cloud.web;

import lombok.val;
import org.cloud.web.configurations.ContextTaskDecorator;
import org.cloud.web.properties.WebStarterProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 描述：开启自动配置
 *
 * @author Tubetrue01@gmail.com 2022/7/22
 */
@EnableConfigurationProperties(WebStarterProperties.class)
@Configuration(proxyBeanMethods = false)
public class WebAutoConfiguration {

    /**
     * 异步任务线程池
     *
     * @param properties 线程池配置属性
     * @return 线程池实例
     */
    @Bean
    public Executor asyncExecutor(WebStarterProperties properties) {
        val poolConfig = properties.getExecutorPool();
        val executor = new ThreadPoolTaskExecutor();
        executor.setTaskDecorator(new ContextTaskDecorator());
        executor.setCorePoolSize(poolConfig.getCorePoolSize());
        executor.setMaxPoolSize(poolConfig.getMaxPoolSize());
        executor.setQueueCapacity(poolConfig.getQueueCapacity());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix(poolConfig.getThreadNamePrefix());
        executor.initialize();
        return executor;
    }
}