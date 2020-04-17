package com.teligen.demo.exception;


public class BusinessException extends RuntimeException {
    /**
     * 错误代码
     */
    private int code = 0;
    /**
     * 错误信息
     */
    private String message = "";

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"code\":")
                .append(code);
        sb.append(",\"message\":\"")
                .append(message).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public BusinessException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
