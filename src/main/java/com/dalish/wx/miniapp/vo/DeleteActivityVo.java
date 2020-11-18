package com.dalish.wx.miniapp.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteActivityVo {
    @NotNull(message = "id is null")
    private Integer id;
}
