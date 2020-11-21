package com.dalish.wx.miniapp.vo;

import lombok.Data;

import java.util.Date;

@Data
public class GetActivityVo {
    private String category;

    private String address;

    private Integer minPrice;

    private Integer maxPrice;

    private Date startDate;

    private Date endDate;
}
