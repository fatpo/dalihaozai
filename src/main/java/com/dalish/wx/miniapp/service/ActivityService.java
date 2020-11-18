package com.dalish.wx.miniapp.service;

import com.dalish.wx.miniapp.entity.Activity;
import com.dalish.wx.miniapp.entity.ActivityExample;
import com.dalish.wx.miniapp.mapper.ActivityMapper;
import com.dalish.wx.miniapp.utils.Lunar;
import com.dalish.wx.miniapp.utils.LunarSolarConverter;
import com.dalish.wx.miniapp.utils.Solar;
import com.dalish.wx.miniapp.vo.GetActivityRspVo;
import com.dalish.wx.miniapp.vo.GetActivityVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ActivityService {
    private static final ThreadLocal<SimpleDateFormat> formatterTl = ThreadLocal
        .withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
    @Autowired
    private ActivityMapper activityMapper;

    public int insertActivity(Activity activity) {
        ActivityExample example = new ActivityExample();
        ActivityExample.Criteria c = example.createCriteria();
        String title = activity.getTitle();
        c.andTitleEqualTo(title);

        List<Activity> dbActivities = activityMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(dbActivities)) {
            log.info("当前数据库无该记录，插入，title: {}", title);
            activityMapper.insert(activity);
            return activity.getId();
        } else {
            log.error("当前数据库存在该记录，请检查，title: {}", title);
            return -1;
        }
    }


    public boolean updateActivity(Activity activity) {
        Integer id = activity.getId();
        Activity dbActivity = activityMapper.selectByPrimaryKey(id);
        String title = activity.getTitle();
        if (dbActivity == null) {
            log.error("更新活动失败，找不到该活动, id: {}, title: {}", id, title);
            return false;
        } else {
            int rows = activityMapper.updateByPrimaryKeySelective(activity);
            log.error("更新活动id: {}, title: {}, 影响行数:{}", id, title, rows);
            if (rows == 1) {
                return true;
            } else if (rows > 1) {
                log.error("活动:{} 影响行数异常，请检查活动是否冗余!", title);
                return true;
            } else {
                return false;
            }
        }
    }

    public void deleteActivity(Integer activityId) {
        int rows = activityMapper.deleteByPrimaryKey(activityId);
        log.info("删除活动，activityId: {}，删除结果:{}", activityId, rows);
    }


    public List<GetActivityRspVo> getActivity(GetActivityVo getActivityVo) {
        ActivityExample example = new ActivityExample();
        ActivityExample.Criteria c = example.createCriteria();

        // 请求可能带了分类查询
        String category = getActivityVo.getCategory();
        if (StringUtils.isNotBlank(category)) {
            c.andCategoryEqualTo(category);
        }

        // 以开始日期为基准，设置活动范围区间
        c.andStartDateGreaterThanOrEqualTo(getActivityVo.getStartDate());
        c.andStartDateLessThanOrEqualTo(getActivityVo.getEndDate());

        List<Activity> activities = activityMapper.selectByExample(example);
        log.info("获取活动，request param : {}，活动size: {}", getActivityVo, activities.size());

        // 组装下
        List<GetActivityRspVo> returnActivities = new ArrayList<>();
        for (Activity activity : activities) {
            GetActivityRspVo rspVo = new GetActivityRspVo();
            BeanUtils.copyProperties(activity, rspVo);
            returnActivities.add(rspVo);

            // 补充农历起始、结束日期
            Date startDate = activity.getStartDate();
            rspVo.setStartDate(formatterTl.get().format(startDate));
            Date endDate = activity.getEndDate();
            rspVo.setEndDate(formatterTl.get().format(endDate));

            Solar startDateSolar = new Solar(startDate);
            Lunar startDateLunar = LunarSolarConverter.SolarToLunar(startDateSolar);
            rspVo.setLunarStartDate(startDateLunar.toDateStr());

            Solar endDateSolar = new Solar(endDate);
            Lunar endDateLunar = LunarSolarConverter.SolarToLunar(endDateSolar);
            rspVo.setLunarEndDate(endDateLunar.toDateStr());
        }
        return returnActivities;
    }
}
