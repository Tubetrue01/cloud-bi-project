package org.cloud.web.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 描述：将配置信息对外暴露
 *
 * @author Tubetrue01@gmail.com by 2022/7/23
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "cloud-bi-web")
public class WebStarterProperties {
    /**
     * 异步任务的超时时间（单位：毫秒），默认 60 秒
     */
    private long asyncTimeout = 60000;

    /**
     * 异步任务线程池配置
     */
    @NestedConfigurationProperty
    private ExecutorPool executorPool = new ExecutorPool();

    @Setter
    @Getter
    public static class ExecutorPool {
        /**
         * 核心线程数
         */
        private int corePoolSize = 10;
        /**
         * 最大线程数
         */
        private int maxPoolSize = 50;
        /**
         * 队列容量
         */
        private int queueCapacity = 500;
        /**
         * 线程名前缀
         */
        private String threadNamePrefix = "Async-Executor-";
    }
}
