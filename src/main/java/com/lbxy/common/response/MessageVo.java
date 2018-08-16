package com.lbxy.common.response;

public class MessageVo<T> {
    private int status;
    private String message;
    private T data;

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

    public T getData() {
        return data;
    }

    public MessageVo setData(T data) {
        this.data = data;
        return this;
    }
}
