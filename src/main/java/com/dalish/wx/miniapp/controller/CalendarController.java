package com.dalish.wx.miniapp.controller;

import com.dalish.wx.miniapp.service.CalendarService;
import com.dalish.wx.miniapp.utils.ReturnCode;
import com.dalish.wx.miniapp.utils.ReturnObj;
import com.dalish.wx.miniapp.vo.GetCalendarRspVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calendar")
@Slf4j
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @GetMapping("/get")
    public ReturnObj<GetCalendarRspVo> getCalendar() {
        long startTime = System.currentTimeMillis();
        log.info("获取日历入参：无");
        try {
            GetCalendarRspVo rspVo = calendarService.getCalendarRspVo();
            return new ReturnObj<>(ReturnCode.SUCCESS, rspVo);
        } catch (Exception ex) {
            log.error("获取日历异常 ", ex);
            return new ReturnObj<>(ReturnCode.SERVER_ERROR);
        } finally {
            log.info("获取日历耗时: {}ms", System.currentTimeMillis() - startTime);
        }
    }


}
