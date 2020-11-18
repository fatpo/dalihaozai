package com.dalish.wx.miniapp.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdateActivityVo {
    @NotNull(message = "id is null")
    private Integer id;

    private String title;

    private String linkUrl;

    private String detail;

    private String category;

    private Date startDate;

    private Date endDate;

    private String startTime;

    private String endTime;

}
