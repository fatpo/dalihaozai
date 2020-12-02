package com.dalish.wx.miniapp.recall;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserCfRecallService implements RecallStrategy {
    @Override
    public List<String> recall(Object recallModel) {
        return Arrays.asList("11","22","33");
    }
}
