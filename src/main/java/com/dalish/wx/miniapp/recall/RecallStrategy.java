package com.dalish.wx.miniapp.recall;

import java.util.List;

public interface RecallStrategy {
    List<String> recall(Object recallModel);
}
