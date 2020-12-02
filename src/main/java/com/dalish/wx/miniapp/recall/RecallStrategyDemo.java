package com.dalish.wx.miniapp.recall;


public class RecallStrategyDemo {

    public static void main(String[] args) {
        // 实验中心传过来： ITEM_CF 这个字样！
        System.out.println(RecallStrategyContext.valueOf("ITEM_CF").recall(null));

        // 实验中心传过来： USER_CF 这个字样！
        System.out.println(RecallStrategyContext.valueOf("USER_CF").recall(null));
    }
}

