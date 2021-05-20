package cn.zsw.campus.forum.common;

import lombok.Getter;

@Getter
public enum CodeEnum {
    /**
     * 请求成功
     */
    REQUEST_SUCCEED(200),

    /**
     * 请求失败
     */
    REQUEST_FAILED(401),

    /**
     * 访问路径不存在
     */
    URI_NOT_EXIST(404),
    /**
     * 系统错误
     */
    SYSTEM_ERROR(501),
    JW_SYSTEM_ERROR(502),
    /**
     * 被禁言
     */
    FORBIDDEN_USER(10086),

    SENSITIVE_WORD_FORBIDDEN(10010);

    private Integer code;

    CodeEnum(Integer code) {
        this.code = code;
    }


}
