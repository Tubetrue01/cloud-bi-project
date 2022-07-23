package org.cloud.web.enums;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * 描述：封装 API 的错误码，通用的状态码定义，子类需要继承该接口
 * <p>
 * 错误码规范：
 * 错误码使用 6 位数字表示
 * 前三位表示业务类型 {@link #getCodeSegment()}
 * 后三位的 [000,1000) 表示当前业务内错误码 {@link #getCodeValue()}。
 * <p>
 * 为了规范使用我们做特殊说明如下：
 * - 100000 保留为成功码 {@link GenericStatusCode#SUCCESS}
 * - [100000,200000) 保留为通用响应码 {@link GenericStatusCode}。
 *
 * @author Tubetrue01@gmail.com by 2022/7/23
 */
public interface IStatusCode extends Serializable {

    /**
     * 码段，需要申请
     */
    int getCodeSegment();

    /**
     * 码值，[000,1000)
     */
    int getCodeValue();

    /**
     * 最终响应码 = (码段 * 1000) + 码值
     */
    default int getCode() {
        return getCodeSegment() * 1000 + getCodeValue();
    }

    /**
     * 响应码信息，用于展示给客户端
     *
     * @return 返回对应响应码的描述信息 msg
     */
    String getMsg();

    /**
     * 响应码详细信息，仅用于详细描述错误信息，更精确的定位问题。
     * <p>
     * 考虑存在的情况：{@link #getMsg()} 返回的信息一样
     * 例如：不同异常的错误码对应的描述都是 "服务似乎出了点问题"，但是详细信息不同。
     */
    default String getDescription(Object... placeholder) {
        return MessageFormat.format(getMsg(), placeholder);
    }
}
