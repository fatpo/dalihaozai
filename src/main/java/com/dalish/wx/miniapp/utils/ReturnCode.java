package com.dalish.wx.miniapp.utils;


public enum ReturnCode {
    SUCCESS(0, "SUCCESS"),
    PARAM_ERROR(40001, "param error"),
    SERVER_ERROR(40002, "server busy, try later"),
    BUSINESS_ERROR(40003, "business error, check log"),
    PASSWORD_ERROR(41001, "password error");

    private final Integer code;
    private final String message;


    ReturnCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
