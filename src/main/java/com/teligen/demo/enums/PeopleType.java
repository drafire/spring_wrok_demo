package com.teligen.demo.enums;

public enum PeopleType {
    STUDENT(1,"学生"),
    TEACHER(2,"老师"),
    ;
    private int code;
    private String msg;

    PeopleType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
