package com.dalish.wx.miniapp.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginVo {
    @NotBlank(message = "name is blank")
    private String name;

    @NotBlank(message = "password is blank")
    private String password;
}
