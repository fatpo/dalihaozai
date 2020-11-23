package com.dalish.wx.miniapp.vo;

import lombok.Data;

@Data
public class GetActivityRspVo {
    private Integer id;

    private String title;

    private String linkUrl;

    private String category;

    private String address;

    private Integer price;

    private String startDate;

    private String endDate;

    private String startTime;

    private String endTime;

    private String lunarStartDate;

    private String lunarEndDate;

    private String dateRange;

    private String timeRange;
}
