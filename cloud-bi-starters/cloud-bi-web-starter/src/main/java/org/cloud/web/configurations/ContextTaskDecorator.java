package org.cloud.web.configurations;

import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 描述：异步任务包装器，用于线程之间传递上下文信息
 *
 * @author Tubetrue01@gmail.com by 2022/7/23
 */
public class ContextTaskDecorator implements TaskDecorator {

    /**
     * 绑定 RequestAttributes 到当前线程
     */
    @Override
    public Runnable decorate(Runnable runnable) {
        var requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return () -> {
            try {
                RequestContextHolder.setRequestAttributes(requestAttributes);
                runnable.run();
            } finally {
                RequestContextHolder.resetRequestAttributes();
            }
        };
    }

}
