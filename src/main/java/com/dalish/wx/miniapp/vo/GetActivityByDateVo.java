package com.dalish.wx.miniapp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class GetActivityByDateVo {
    @JsonFormat(pattern = "yyyy-M-d")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-M-d")
    private Date endDate;

}
