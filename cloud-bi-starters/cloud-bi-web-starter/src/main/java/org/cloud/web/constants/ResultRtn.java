package org.cloud.web.constants;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * 通用的返回结果
 *
 * @param <T> 返回数据体
 * @author Tubetrue01@gmail.com by 2021/2/1
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultRtn<T> {
    /**
     * 响应码
     */
    private int code;
    /**
     * 响应信息
     */
    private String msg;
    /**
     * 数据对象，无数据默认为 null
     */
    private Data<T> data;
    /**
     * 调试信息：不可用于业务
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object debugMsg;

    /**
     * 简单的成功返回响应结果
     */
    public static <T> ResultRtn<T> success() {
        return success(null);
    }

    /**
     * 成功加数据的返回结果
     *
     * @param t   结果数据
     * @param <T> 数据的类型
     */
    public static <T> ResultRtn<T> success(T t) {
        return of(GenericStatusCode.SUCCESS, t);
    }

    /**
     * 失败响应结果
     */
    public static <T> ResultRtn<T> fail() {
        return of(GenericStatusCode.ERROR, null);
    }

    /**
     * 自定义响应码的失败响应结果
     *
     * @param statusCode 响应状态码
     */
    public static <T> ResultRtn<T> fail(GenericStatusCode statusCode) {
        return of(statusCode, null);
    }

    /**
     * 自定义响应码的失败响应结果，同时支持系统异常的输出
     *
     * @param statusCode 状态码
     * @param e          异常对象
     */
    public static <T> ResultRtn<T> fail(GenericStatusCode statusCode, Throwable e) {
        final ResultRtn<T> of = of(statusCode, e.getMessage(), e);
        return of.debug(e);
    }

    /**
     * 只含状态码的结果集，适用于无数据返回的场景
     *
     * @param statusCode 状态码
     * @param <T>        null 值
     */
    public static <T> ResultRtn<T> of(GenericStatusCode statusCode) {
        return of(statusCode, null);
    }

    /**
     * 含有动态值的错误信息描述，只有异常时才会有动态值概念
     *
     * @param statusCode 状态码
     * @param detail     错误描述
     * @param e          异常对象
     */
    private static <T> ResultRtn<T> of(GenericStatusCode statusCode, String detail, Throwable e) {
        var result = new ResultRtn<T>();
        result.setCode(statusCode.getCodeValue());
        result.setMsg(detail);
        result.debug(e);
        return result;
    }

    /**
     * 状态码加数据的响应结果
     *
     * @param statusCode 状态码
     * @param t          数据对象
     * @param <T>        数据的泛型定义
     */
    @SuppressWarnings("rawtypes")
    public static <T> ResultRtn<T> of(GenericStatusCode statusCode, T t) {
        if (t instanceof com.github.pagehelper.Page) {
            var pageInfo = (com.github.pagehelper.Page) t;
            return of(statusCode, PageInfo.of(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal()), t);
        }
        return of(statusCode, null, t);
    }

    /**
     * 状态码加带分页数据的响应结果
     *
     * @param statusCode 状态码
     * @param pageInfo   分页对象
     * @param t          业务数据
     * @param <T>        数据的泛型定义
     */
    private static <T> ResultRtn<T> of(GenericStatusCode statusCode, PageInfo pageInfo, T t) {
        var result = new ResultRtn<T>();
        var data = new Data<T>();
        result.setCode(statusCode.getCodeValue());
        result.setMsg(statusCode.getMsg());
        data.setPageInfo(pageInfo);
        data.setDataInfo(t);
        result.setData(data);
        return result;
    }

    /**
     * 调试信息处理，将目标方法发生的异常进行回显
     *
     * @param e 异常对象
     */
    private ResultRtn<T> debug(Throwable e) {
        val s = e.getClass().getSimpleName();
        val message = e.getLocalizedMessage();
        this.debugMsg = (message != null) ? (s + ": " + message) : s;
        return this;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class PageInfo {
        /**
         * 页码
         */
        private final int pageNum;
        /**
         * 每页展示的大小
         */
        private final int pageSize;
        /**
         * 总的页数
         */
        private final long pageCount;
        /**
         * 该条件下总共的数据条数
         */
        private final long total;

        public static PageInfo of(int pageNum, int pageSize, long total) {
            // 根据每页的记录数，计算总的页数
            val pageCount = (total + pageSize - 1) / pageSize;
            return new PageInfo(pageNum, pageSize, pageCount, total);
        }
    }

    /**
     * 具体的数据对象，包括：分页对象、业务数据对象
     *
     * @param <T> 业务数据对象类型
     */
    @Getter
    @Setter(AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Data<T> {
        /**
         * 分页数据对象
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private PageInfo pageInfo;
        /**
         * 业务数据对象
         */
        private T dataInfo;
    }
}
