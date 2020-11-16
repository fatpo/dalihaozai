package com.dalish.wx.miniapp.service;

import com.dalish.wx.miniapp.entity.Activity;
import com.dalish.wx.miniapp.mapper.ActivityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActivityService {
    @Autowired
    private ActivityMapper activityMapper;


    public void syncActivity(Activity activity) {
        activityMapper.insert(activity);
    }
}
