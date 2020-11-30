package com.dalish.wx.miniapp.vo;

import lombok.Data;

import java.util.List;

@Data
public class GetActivityByDateRspVo {

    private String date;

    private List<GetActivityRspVo> list;

}
