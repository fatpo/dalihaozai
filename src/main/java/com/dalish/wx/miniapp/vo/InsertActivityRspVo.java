package com.dalish.wx.miniapp.vo;

import lombok.Data;

import java.util.Date;

@Data
public class InsertActivityRspVo {
    private Integer id;

    private String title;

    private String linkUrl;

    private String category;

    private Date startDate;

    private Date endDate;

    private String startTime;

    private String endTime;

}
