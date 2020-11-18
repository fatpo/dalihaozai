package com.dalish.wx.miniapp.vo;

import lombok.Data;

@Data
public class GetActivityRspVo {
    private String title;

    private String linkUrl;

    private String category;

    private String startDate;

    private String endDate;

    private String startTime;

    private String endTime;

    private String lunarStartDate;

    private String lunarEndDate;
}
