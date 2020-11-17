package com.dalish.wx.miniapp.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeleteActivityVo {
    @NotBlank(message = "title is blank")
    private String title;
}
