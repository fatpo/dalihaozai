package com.dalish.wx.miniapp.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateActivityVo {
    private Integer id;

    private String title;

    private String linkUrl;

    private String detail;

    private String category;

    private Integer price;

    private String address;

    private Date startDate;

    private Date endDate;

    private String startTime;

    private String endTime;

}
