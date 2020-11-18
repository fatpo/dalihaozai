package com.dalish.wx.miniapp.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class GetActivityVo {
    private String category;

    @NotNull(message = "startDate is blank")
    private Date startDate;

    @NotNull(message = "endDate is blank")
    private Date endDate;
}
