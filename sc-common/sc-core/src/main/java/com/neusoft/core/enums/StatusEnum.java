
package com.neusoft.core.enums;

public enum StatusEnum {
    NORMAL_STATUS(1, "正常"),
    STOP_STATUS(0, "停用"),
    NO_START_STATUS(-1, "未启用");

    private Integer code;
    private String msg;

    private StatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
