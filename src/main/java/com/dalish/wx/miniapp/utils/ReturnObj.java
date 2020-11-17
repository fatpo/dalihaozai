package com.dalish.wx.miniapp.utils;

import lombok.Data;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
public class ReturnObj<T> {
    private Integer code;
    private String message;
    private T data;

    public ReturnObj(ReturnCode returnCode){
        this.code = returnCode.getCode();
        this.message = returnCode.getMessage();
    }

    public ReturnObj(Integer code, String message ){
        this.code = code;
        this.message = message;
    }
}
