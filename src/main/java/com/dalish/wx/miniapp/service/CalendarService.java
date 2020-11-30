package com.dalish.wx.miniapp.service;

import com.dalish.wx.miniapp.utils.LunarUtil;
import com.dalish.wx.miniapp.vo.CalendarVo;
import com.dalish.wx.miniapp.vo.CalendarMonthVo;
import com.dalish.wx.miniapp.vo.GetCalendarRspVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
public class CalendarService {
    @Value("${calendar.monthCnt:2}")
    int monthCnt;

    public GetCalendarRspVo getCalendarRspVo(){
        // 组装当天数据
        GetCalendarRspVo rspVo = new GetCalendarRspVo();
        Calendar calendar = Calendar.getInstance();
        rspVo.setTodayYear(calendar.get(Calendar.YEAR));
        rspVo.setTodayMonth(calendar.get(Calendar.MONTH));
        rspVo.setTodayDay(calendar.get(Calendar.DATE));

        // 组装每个月数据
        List<CalendarMonthVo> monthList = getMonthList(monthCnt);
        rspVo.setMonthList(monthList);
        return rspVo;
    }

    private List<CalendarMonthVo> getMonthList(int monthCnt) {
        Calendar calendar = Calendar.getInstance();
        int cnt = 0;

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        List<CalendarMonthVo> rspVos = new ArrayList<>();
        while (cnt++ < monthCnt) {
            month++;
            if (month == 13) {
                month = 1;
                year += 1;
            }

            log.info("处理{}年，{}月", year, month);
            CalendarMonthVo rspVo = new CalendarMonthVo();
            rspVo.setYear(year);
            rspVo.setMonth(month);
            rspVo.setWeekList(getWeekList(year, month));
            rspVos.add(rspVo);
        }
        return rspVos;
    }


    public List<List<CalendarVo>> getWeekList(int year, int month) {
        //input
        Calendar calendar = Calendar.getInstance();
        int javaOffset = 1;
        //noinspection MagicConstant
        calendar.set(year, month - javaOffset, 1);
        int startDay = calendar.get(Calendar.DAY_OF_WEEK); //求本週第一天是星期幾
        int count = startDay - 1; //第一週的初始計數
        int maxDay = maxDayInMonth(year, month);

        List<List<CalendarVo>> rsp = new ArrayList<>();
        List<CalendarVo> tmpRsp = new ArrayList<>();
        for (int i = 1; i < startDay; i++) {
            tmpRsp.add(null);
        }
        for (int day = 1; day <= maxDay; day++) {
            CalendarVo calendarVo = new CalendarVo();
            calendarVo.setDay(day);
            calendarVo.setDate(String.format("%04d-%02d-%02d", year, month, day));
            Calendar c = Calendar.getInstance();
            //noinspection MagicConstant
            c.set(year, month - javaOffset, day);

            LunarUtil cc = new LunarUtil(c);
            calendarVo.setLunarDate(cc.toString());
            tmpRsp.add(calendarVo);

            count++;
            if (count >= 7) { //每輸出7天換一次行
                count = 0;

                rsp.add(new ArrayList<>(tmpRsp));
                tmpRsp.clear();
            }
        }
        // 不足补 7
        while (tmpRsp.size() < 7){
            tmpRsp.add(null);
        }
        rsp.add(new ArrayList<>(tmpRsp));
        return rsp;
    }

    public int maxDayInMonth(int year, int month) {
        int max = 30;
        if (month == 1 | month == 3 | month == 5 | month == 7 | month == 8 | month == 10 | month == 12) max = 31;
        else if (month == 2) max = 28;
        else if (year % 400 == 0) max = 29;
        return max;
    }

}
