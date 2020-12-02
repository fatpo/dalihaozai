package com.dalish.wx.miniapp.recall;

import java.util.ArrayList;
import java.util.List;

public class ChainOfResponsibility {
    public static void main(String[] args) {
        Msg msg = new Msg();
        msg.str = "hello world! fatpo! 996.icu!  - v -";

        FilterChain filterChain = new FilterChain();
        filterChain.addFilter(new DigitFilter())
            .addFilter(new FatpoFilter())
            .addFilter(new FaceFilter());
        filterChain.doFilter(msg);

        System.out.println(msg.str);
    }
}

class Msg {
    String str;
}

interface Filter {
    boolean doFilter(Msg msg);
}

/**
 * 过滤数字
 */
class DigitFilter implements Filter {
    @Override
    public boolean doFilter(Msg msg) {
        System.out.println("数字过滤器...");
        msg.str = msg.str.replaceAll("996", "855");
        return true;
    }
}

/**
 * 过滤表情
 */
class FaceFilter implements Filter {
    @Override
    public boolean doFilter(Msg msg) {
        System.out.println("表情过滤器...");
        msg.str = msg.str.replaceAll("- v -", "￥-￥");
        return true;
    }
}

/**
 * 过滤肥婆
 */
class FatpoFilter implements Filter {
    @Override
    public boolean doFilter(Msg msg) {
        System.out.println("肥婆过滤器...");
        // 出现肥婆，就中断责任链
        if (msg.str.contains( "fatpo")){
            msg.str = msg.str.replaceAll("fatpo", "肥婆");
            return false;
        }
        return true;
    }
}

class FilterChain  implements Filter{
    List<Filter> filters = new ArrayList<>();

    @Override
    public boolean doFilter(Msg msg) {
        for (Filter filter : filters) {
            if (!filter.doFilter(msg)) {
                return false;
            }
        }
        return true;
    }

    public FilterChain addFilter(Filter filter) {
        this.filters.add(filter);
        return this;
    }
}
