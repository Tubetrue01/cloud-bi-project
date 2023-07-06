package org.cloud.web.exception;

import lombok.extern.log4j.Log4j2;
import org.cloud.web.constants.GenericStatusCode;
import org.cloud.web.constants.ResultRtn;
import org.cloud.web.utils.WebUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * 描述：全局异常处理器
 *
 * @author Tubetrue01@gmail.com by 2021/2/1
 */
@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常通用处理
     *
     * @param exception 业务异常
     * @return 返回业务异常信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(value = BaseException.class)
    public ResultRtn<Void> baseExceptionHandler(BaseException exception) {
        var statusCode = exception.getGenericStatusCode();
        log.warn("业务发生异常，请求地址 [{}]，错误码 [{}]，错误码信息 [{}]",
                obtainRequestUrl(), statusCode.getCodeValue(), exception.getMessage(), exception.getCause());
        return ResultRtn.fail(statusCode, exception);
    }

    /**
     * 参数异常捕获处理，如：400
     *
     * @return 返回参数非法响应结果
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    public ResultRtn<Void> validationExceptionHandler(Exception exception) {
        // 封装错误集合
        var errorResult = new ArrayList<Map<String, String>>();

        if (exception instanceof MethodArgumentNotValidException) {
            var methodArgumentNotValidException = (MethodArgumentNotValidException) exception;
            obtainErrorResult(methodArgumentNotValidException.getBindingResult(), errorResult);
        } else if (exception instanceof BindException) {
            var bindException = (BindException) exception;
            obtainErrorResult(bindException.getBindingResult(), errorResult);
        }
        log.warn("请求地址:[{}],参数不合法,异常信息: {}", obtainRequestUrl(), errorResult);
        return ResultRtn.fail(GenericStatusCode.PARAM_VALIDATE_EXCEPTION, exception);
    }

    /**
     * 参数校验异常捕获处理
     *
     * @param exception 参数校验异常
     * @return 返回参数非法响应
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultRtn<Void>
    constraintViolationException(ConstraintViolationException exception) {
        log.warn("请求地址:[{}],参数校验异常,异常类型:[{}],异常信息: ", exception.getClass().getSimpleName(),
                exception);
        return ResultRtn.fail(GenericStatusCode.PARAM_VALIDATE_EXCEPTION, exception);
    }

    /**
     * 资源不存在异常处理，如：404
     *
     * @param exception 资源不存在异常类
     * @return 返回请求资源不存在响应结果
     */
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResultRtn<Void> notFoundException(NoHandlerFoundException exception) {
        log.warn("请求资源: [{}] 未找到", exception.getRequestURL());
        return ResultRtn.fail(GenericStatusCode.RESOURCE_NOT_FOUND, exception);
    }

    /**
     * 请求方法不支持异常处理，如：405
     *
     * @param exception 异常类
     * @return 返回方法不受支持响应结果
     */
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResultRtn<Void>
    methodNotAllowException(HttpRequestMethodNotSupportedException exception) {
        log.warn("请求地址: [{}], 请求方法 [{}] 不支持", obtainRequestUrl(), exception.getMethod());
        return ResultRtn.fail(GenericStatusCode.METHOD_NOT_ALLOW, exception);
    }

    /**
     * 拦截 json 转换异常
     *
     * @param exception 消息不可读异常对象
     * @return 返回参数非法响应结果
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResultRtn<Void>
    httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.warn("请求地址: [{}], 异常类型:[{}], 异常信息：", obtainRequestUrl(), exception.getClass().getSimpleName(), exception);
        return ResultRtn.fail(GenericStatusCode.PARAM_VALIDATE_EXCEPTION, exception);
    }

    /**
     * 系统内部异常，如：空指针等，并且打印堆栈信息
     *
     * @param exception 异常类
     * @return 返回系统内部错误响应结果
     */
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public ResultRtn<Void> internalExceptionHandler(Exception exception) {
        log.warn("请求地址: [{}], 异常类型: [{}], 异常信息: ", obtainRequestUrl(), exception.getClass().getSimpleName(),
                exception);
        return ResultRtn.fail(GenericStatusCode.INTERNAL_EXCEPTION, exception);
    }

    /**
     * 获取错误结果信息
     *
     * @param bindingResult 绑定异常对象
     * @param errorResult   存放结果对象
     */
    private void obtainErrorResult(BindingResult bindingResult, List<Map<String, String>> errorResult) {
        bindingResult.getFieldErrors()
                .forEach(fieldError -> {
                    var field = fieldError.getField();
                    var error = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
                    var map = new HashMap<String, String>();
                    map.put(field, error);
                    errorResult.add(map);
                });
    }

    /**
     * 获取当前请求的路径
     *
     * @return 返回请求路径
     */
    private String obtainRequestUrl() {
        return WebUtil.getCurrentRequestUrl();
    }
}
