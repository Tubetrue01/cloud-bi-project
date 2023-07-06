package org.cloud.web.exception;

import lombok.Getter;
import org.cloud.web.constants.GenericStatusCode;

/**
 * 描述：通用的异常基类，业务的相关异常需要继承该类
 *
 * @author Tubetrue01@gmail.com by 2023/5/22
 */
@Getter
public class BaseException extends RuntimeException {
    /**
     * 状态码接口
     */
    private final GenericStatusCode genericStatusCode;

    public BaseException(GenericStatusCode genericStatusCode) {
        super(genericStatusCode.getMsg());
        this.genericStatusCode = genericStatusCode;
    }

    public BaseException(GenericStatusCode genericStatusCode, Object... details) {
        super(genericStatusCode.getDescription(details));
        this.genericStatusCode = genericStatusCode;
    }

    public BaseException(GenericStatusCode genericStatusCode, Throwable throwable) {
        super(throwable);
        this.genericStatusCode = genericStatusCode;
    }
}
