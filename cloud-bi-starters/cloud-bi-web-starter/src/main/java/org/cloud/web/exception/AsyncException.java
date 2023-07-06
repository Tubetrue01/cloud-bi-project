package org.cloud.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.cloud.web.constants.GenericStatusCode;
import org.cloud.web.constants.ResultRtn;
import org.cloud.web.utils.WebUtil;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * 描述：异步异常封装
 *
 * @author Tubetrue01@gmail.com by 2023/5/22
 */
@Slf4j
@SuppressWarnings("all")
public class AsyncException extends RuntimeException {

    public AsyncException(Throwable e, DeferredResult... result) {
        super(e);
        String requestURL = WebUtil.getCurrentRequestUrl();

        if (result != null && result.length > 0) {
            if (e.getCause() instanceof BaseException) {
                BaseException exception = (BaseException) e.getCause();
                result[0].setResult(ResultRtn.of(exception.getGenericStatusCode()));
                log.warn("[{}]-异常信息:[{}]", requestURL, exception.getGenericStatusCode().getMsg(), exception.getCause());
                return;
            } else {
                result[0].setResult(ResultRtn.fail(GenericStatusCode.TIMEOUT_OR_SYSTEM_ERROR, e));
            }
        }
        log.error("[{}]-异常类型:[{}]，异常信息：", requestURL, e.getClass().getSimpleName(), e);
    }
}
