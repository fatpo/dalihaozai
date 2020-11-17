package com.dalish.wx.miniapp.service;

import com.dalish.wx.miniapp.entity.Activity;
import com.dalish.wx.miniapp.entity.ActivityExample;
import com.dalish.wx.miniapp.mapper.ActivityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class ActivityService {
    @Autowired
    private ActivityMapper activityMapper;


    public void syncActivity(Activity activity) {
        ActivityExample example = new ActivityExample();
        ActivityExample.Criteria c = example.createCriteria();
        String title = activity.getTitle();
        c.andTitleEqualTo(title);

        List<Activity> a = activityMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(a)) {
            log.info("当前数据库无该记录，插入，title: {}", title);
            activityMapper.insert(activity);
        } else {
            log.info("当前数据库存在该记录，覆盖，title: {}", title);
            activityMapper.updateByExampleSelective(activity, example);
        }
    }

    public void deleteActivity(String title) {
        ActivityExample example = new ActivityExample();
        ActivityExample.Criteria c = example.createCriteria();
        c.andTitleEqualTo(title);

        int rows = activityMapper.deleteByExample(example);
        log.info("删除活动，title: {}，删除结果:{}", title, rows);
    }
}
