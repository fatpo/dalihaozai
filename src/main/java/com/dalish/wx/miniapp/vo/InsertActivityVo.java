package com.dalish.wx.miniapp.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class InsertActivityVo {
    @NotBlank(message = "title is blank")
    private String title;

    @NotBlank(message = "linkUrl is blank")
    private String linkUrl;

    private String detail;

    @NotBlank(message = "category is blank")
    private String category;

    @NotBlank(message = "address is blank")
    private String address;

    private Integer price;

    @NotNull(message = "startDate is blank")
    private Date startDate;

    @NotNull(message = "endDate is blank")
    private Date endDate;

    @NotBlank(message = "startTime is blank")
    private String startTime;

    @NotBlank(message = "endTime is blank")
    private String endTime;

}
