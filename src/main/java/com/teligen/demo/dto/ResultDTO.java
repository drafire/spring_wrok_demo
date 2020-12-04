package com.teligen.demo.dto;

public class ResultDTO<T> {
    private boolean result;
    private int code;
    private String msg;
    private int totalNumber;
    private T data;

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public ResultDTO(boolean result, int code, String msg, int totalNumber, T data) {
        this.result = result;
        this.code = code;
        this.msg = msg;
        this.totalNumber = totalNumber;
        this.data = data;
    }

    public static ResultDTO success() {
        return success(0, null);
    }

    public static <T> ResultDTO success(T data) {
        return success(ResultCode.SUCCESS.getDes(), 1, data);
    }

    public static <T> ResultDTO success(int totalNum, T data) {
        return success(ResultCode.SUCCESS.getDes(), totalNum, data);
    }

    public static ResultDTO success(String msg, int totalNum) {
        return success(ResultCode.SUCCESS.getCode(), msg, totalNum, null);
    }

    public static ResultDTO success(int code, String msg, int totalNum) {
        return success(code, msg, totalNum, null);
    }

    public static <T> ResultDTO success(String msg, int totalNum, T data) {
        return success(ResultCode.SUCCESS.getCode(), msg, totalNum, data);
    }

    public static <T> ResultDTO success(int code, String msg, int totalNum, T data) {
        return new ResultDTO(true, code, msg, totalNum, data);
    }

    public static ResultDTO fail() {
        return fail(null);
    }

    public static ResultDTO fail(int code) {
        return fail(code, ResultCode.FAIL.getDes(), null);
    }

    public static ResultDTO fail(int code, String message) {
        return fail(code, message, null);
    }

    public static ResultDTO fail(Object data) {
        return fail(ResultCode.FAIL.getDes(), data);
    }

    public static ResultDTO fail(String msg, Object data) {
        return fail(ResultCode.FAIL.getCode(), msg, data);
    }

    public static ResultDTO fail(int code, String msg, Object data) {
        return new ResultDTO(false, code, msg, 0, data);
    }

    public static ResultDTO noData() {
        return noData(ResultCode.NODATA.getCode(), ResultCode.NODATA.getDes());
    }

    public static <T> ResultDTO noData(int code) {
        return noData(code, ResultCode.NODATA.getDes());
    }

    public static <T> ResultDTO noData(String msg) {
        return noData(ResultCode.NODATA.getCode(), msg);
    }

    public static <T> ResultDTO noData(int code, String msg) {
        return new ResultDTO(true, code, msg, 0, new String[]{});
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"result\":")
                .append(result);
        sb.append(",\"code\":")
                .append(code);
        sb.append(",\"msg\":\"")
                .append(msg).append('\"');
        sb.append(",\"totalNumber\":")
                .append(totalNumber);
        sb.append(",\"data\":")
                .append(data);
        sb.append('}');
        return sb.toString();
    }
}
