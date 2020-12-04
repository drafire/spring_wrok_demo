package com.teligen.demo.dto;

public enum ResultCode {
    SUCCESS(1, "成功"),
    FAIL(-1, "失败"),
    UPLOAD_PHOTO_FAIL(-2, "上传图片失败"),
    SAVE_ATTACH_FAIL(-3, "保存附件失败"),
    EXCEPTION(-4, "保存附件失败"),
    SYSTEM_EXCEPTION(-99, "系统异常"),
    EMPTY_TOKEN(-100, "token为空"),
    TOKEN_EXPIRE(-98, "token已过期"),
    METHOD_NOT_SUPPORT(-101,"方法不支持"),
    PARAM_ERROR(-102,"参数错误"),
    NODATA(0, "没有数据");
    private int code;
    private String des;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    ResultCode(int code, String des) {
        this.code = code;
        this.des = des;
    }
}
