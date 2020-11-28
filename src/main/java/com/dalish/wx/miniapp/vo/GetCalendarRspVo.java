package com.dalish.wx.miniapp.vo;

import lombok.Data;

import java.util.List;

@Data
public class GetCalendarRspVo {
    Integer todayYear;
    Integer todayMonth;
    Integer todayDay;
    List<CalendarMonthVo> monthList;
}
