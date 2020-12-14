package com.dalish.wx.miniapp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class GetActivityVo {
    private String category;

    private String address;

    private Integer minPrice;

    private Integer maxPrice;

    @JsonFormat(pattern = "yyyy-M-d")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-M-d")
    private Date endDate;
}
