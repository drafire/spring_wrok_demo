package com.teligen.demo.enums;

public enum SexType {
    MAN("man","男"),
    WOMAN("woman","女"),
    ;
    private String code;
    private String msg;

    SexType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
