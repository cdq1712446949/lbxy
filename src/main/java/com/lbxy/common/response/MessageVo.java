package com.lbxy.common.response;

public class MessageVo {
    private int status;
    private String message;
    private Object data;

    public int getStatus() {
        return status;
    }

    public MessageVo setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MessageVo setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public MessageVo setData(Object data) {
        this.data = data;
        return this;
    }
}
