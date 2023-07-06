package org.cloud.web.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloud.web.exception.AsyncException;
import org.cloud.web.properties.WebStarterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 描述：异步工具类
 *
 * @author Tubetrue01@gmail.com by 2023/5/22
 */
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("all")
public class AsyncUtil<T> {
    private static Executor asyncExecutor;
    private static WebStarterProperties webStarterProperties;

    /**
     * 异步方法封装
     *
     * @param supplier 方法的实现
     * @param result   异步响应对象
     * @param <T>      返回的参数类型
     * @return 返回异步对象
     */
    public static <T> CompletableFuture<T> async(Supplier<T> supplier, DeferredResult... result) {
        return CompletableFuture.supplyAsync(supplier, asyncExecutor)
                .orTimeout(webStarterProperties.getAsyncTimeout(), TimeUnit.SECONDS)
                .exceptionally(e -> {
                    throw new AsyncException(e, result);
                });
    }

    /**
     * 异步方法的封装，当不需要返回值的时候，可以采用该方法
     *
     * @param runnable 需要异步执行的任务
     * @param result   异步响应对象
     * @return 返回空值的异步对象
     */
    public static CompletableFuture<Void> async(Runnable runnable, DeferredResult... result) {
        return CompletableFuture.runAsync(runnable, asyncExecutor)
                .orTimeout(webStarterProperties.getAsyncTimeout(), TimeUnit.SECONDS)
                .exceptionally(e -> {
                    throw new AsyncException(e, result);
                });
    }

    /**
     * 等待指定的异步任务完成
     *
     * @param completableFutureList 异步任务集合
     */
    public static CompletableFuture<Void> allOf(List<CompletableFuture<Void>> completableFutureList) {
        return CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[]{}));
    }

    //------------------------------- 静态注入的一些辅助方法 -------------------------------//
    @Autowired
    private void asyncExecutor(Executor asyncExecutor) {
        setAsyncExecutor(asyncExecutor);
    }

    @Autowired
    private void webStarterProperties(WebStarterProperties webStarterProperties) {
        setWebStarterProperties(webStarterProperties);
    }

    private static void setWebStarterProperties(WebStarterProperties webStarterProperties) {
        AsyncUtil.webStarterProperties = webStarterProperties;
    }

    private static void setAsyncExecutor(Executor asyncExecutor) {
        AsyncUtil.asyncExecutor = asyncExecutor;
    }

}
