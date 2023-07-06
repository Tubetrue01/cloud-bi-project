package org.cloud.web.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 描述：Web相关工具类
 *
 * @author Tubetrue01@gmail.com by 2021/2/1
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WebUtil {
    /**
     * 获取当前请求 request
     *
     * @return 当前请求对象 current request
     * @throws NullPointerException 该方法可能会返回 null 值
     */
    public static Optional<HttpServletRequest> getCurrentRequest() {
        var servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes == null ? Optional.empty() :
                Optional.of(servletRequestAttributes.getRequest());
    }

    /**
     * 获取当前请求的 url
     *
     * @return 如果不存在返回 null
     */
    public static String getCurrentRequestUrl() {
        return getCurrentRequest().map(HttpServletRequest::getRequestURI).orElse(null);
    }

    /**
     * 获取当前请求的 header 头内容
     *
     * @return 如果不存在返回 null
     */
    public static String getCurrentRequestHeader(String headerKey) {
        return getCurrentRequest().map((HttpServletRequest s) -> s.getHeader(headerKey)).orElse(null);
    }

}
