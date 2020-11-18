package com.dalish.wx.miniapp.utils;

public class Lunar {
    public boolean isleap;
    public int lunarDay;
    public int lunarMonth;
    public int lunarYear;

    public String toDateStr() {
        return String.format("%04d-%02d-%02d", lunarYear, lunarMonth, lunarDay);
    }
}
