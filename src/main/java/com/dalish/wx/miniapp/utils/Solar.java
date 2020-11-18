package com.dalish.wx.miniapp.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Solar {
    public int solarDay;
    public int solarMonth;
    public int solarYear;

    public Solar(){}

    public Solar(Date date){
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.solarYear  = localDate.getYear();
        this.solarMonth = localDate.getMonthValue();
        this.solarDay   = localDate.getDayOfMonth();
    }
}
