package org.cloud.web.constants;

import lombok.Getter;

/**
 * 描述：状态码通用类
 *
 * @author Tubetrue01@gmail.com by 2022/7/23
 * @see IStatusCode
 */
@Getter
public enum GenericStatusCode implements IStatusCode {
    // -------------------- 1 - 50 常用基本错误码 --------------------//
    /**
     * 请求成功响应码。注意：对于成功的状态码有多种组合：这里组合是将成功的请求（包括有无数据返回）设置为 SUCCESS，
     * 无数据的情况会设置为对应的空对象而非 Null。如：空对象，返回 "{}",集合对象返回："[]"。
     * 如果无结果集情况作为失败处理的话，那么请使用 NULL_RESULT 状态码。
     */
    SUCCESS(0, "请求成功"),
    /**
     * 请求失败响应码
     */
    ERROR(100001, "操作失败"),
    /**
     * 服务降级
     */
    FALLBACK(100002, "服务降级"),
    /**
     * 请求资源不存在
     */
    RESOURCE_NOT_FOUND(100003, "请求资源不存在"),
    /**
     * 请求方法不支持
     */
    METHOD_NOT_ALLOW(100004, "请求方法不支持"),
    /**
     * 服务内部错误
     */
    INTERNAL_EXCEPTION(100005, "服务似乎出了点问题"),
    /**
     * 空结果集，当无结果情况被认定为 ERROR 的时候，可以采用该状态码。
     */
    NULL_RESULT(100006, "空结果集"),
    /**
     * 配置参数不合法
     */
    CONFIG_VALIDATE_EXCEPTION(100012, "配置参数不合法"),
    /**
     * 参数不合法
     */
    PARAM_VALIDATE_EXCEPTION(100013, "参数不合法"),
    /**
     * 超时或系统错误
     */
    TIMEOUT_OR_SYSTEM_ERROR(100014, "超时或系统错误");

    /**
     * 码值
     */
    public final int codeValue;
    /**
     * 响应码对应描述信息
     */
    public final String msg;

    GenericStatusCode(int codeValue, String msg) {
        this.codeValue = codeValue;
        this.msg = msg;
    }

    /**
     * 码段
     *
     * @return 码段
     */
    @Override
    public int getCodeSegment() {
        return 0;
    }

    /**
     * 是否是成功码
     *
     * @param code code
     * @return true 表示是成功码
     */
    public static boolean success(IStatusCode code) {
        return GenericStatusCode.SUCCESS.getCode() == code.getCode();
    }

    /**
     * 是否是失败码
     *
     * @param code code
     * @return true 表示是失败码
     */
    public static boolean fail(IStatusCode code) {
        return !success(code);
    }
}
