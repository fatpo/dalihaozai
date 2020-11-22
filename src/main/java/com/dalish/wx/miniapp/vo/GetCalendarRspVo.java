package com.dalish.wx.miniapp.vo;

import lombok.Data;

import java.util.List;

@Data
public class GetCalendarRspVo {
    Integer year;
    Integer month;
    List<List<CalendarVo>> list;

}
