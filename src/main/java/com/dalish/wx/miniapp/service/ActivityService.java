package com.dalish.wx.miniapp.service;

import com.dalish.wx.miniapp.entity.Activity;
import com.dalish.wx.miniapp.entity.ActivityExample;
import com.dalish.wx.miniapp.mapper.ActivityMapper;
import com.dalish.wx.miniapp.utils.Lunar;
import com.dalish.wx.miniapp.utils.LunarSolarConverter;
import com.dalish.wx.miniapp.utils.Solar;
import com.dalish.wx.miniapp.vo.GetActivityByDateRspVo;
import com.dalish.wx.miniapp.vo.GetActivityByDateVo;
import com.dalish.wx.miniapp.vo.GetActivityRspVo;
import com.dalish.wx.miniapp.vo.GetActivityVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
        boolean queryFlag = false;

        // 请求可能带了分类查询
        String category = getActivityVo.getCategory();
        if (category.equals("ALL")) {
            log.info("查询全部分类...");
            queryFlag = true;
        } else if (StringUtils.isNotBlank(category)) {
            c.andCategoryEqualTo(category);
            queryFlag = true;
        }

        // 请求可能带了地址查询
        String address = getActivityVo.getAddress();
        if (StringUtils.isNotBlank(address)) {
            c.andAddressLike(address);
            queryFlag = true;
        }

        // 请求可能带了价格查询 - min
        Integer minPrice = getActivityVo.getMinPrice();
        if (minPrice != null) {
            c.andPriceGreaterThan(minPrice);
            queryFlag = true;
        }

        // 请求可能带了价格查询 - max
        Integer maxPrice = getActivityVo.getMaxPrice();
        if (maxPrice != null) {
            c.andPriceLessThan(maxPrice);
            queryFlag = true;
        }

        // 以开始日期为基准，设置活动范围区间
        Date startDate = getActivityVo.getStartDate();
        if (startDate != null) {
            c.andStartDateGreaterThanOrEqualTo(startDate);
            queryFlag = true;
        }
        Date endDate = getActivityVo.getEndDate();
        if (endDate != null) {
            c.andStartDateLessThanOrEqualTo(endDate);
            queryFlag = true;
        }

        // 如果确实有查询条件
        if (queryFlag) {
            List<Activity> activities = activityMapper.selectByExample(example);
            log.info("获取活动，request param : {}，活动size: {}", getActivityVo, activities.size());

            // 组装下
            List<GetActivityRspVo> returnActivities = new ArrayList<>();
            for (Activity activity : activities) {
                GetActivityRspVo rspVo = getActivityRspVo(activity);
                returnActivities.add(rspVo);
            }
            return returnActivities;
        } else {
            return new ArrayList<>();
        }
    }

    public List<GetActivityByDateRspVo> getActivityByDate(GetActivityByDateVo getActivityVo) {
        /*
        *  select * from activity where
             (start_date <= "2020-12-01" and end_date >= '2020-12-08')  -- 活动把查询条件包起来
             or
             (start_date >= "2020-12-01" and end_date <= '2020-12-08')  -- 活动被查询条件包起来
             or
             (start_date <= "2020-12-01" and end_date <= '2020-12-08' and end_date >= '2020-12-01')  -- 活动在查询条件的左边
             or
             (start_date >= "2020-12-01" and end_date >= '2020-12-08' and start_date <= '2020-12-08') -- 活动在查询条件的右边 ;
        * */

        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);

        List<Activity> dbActivities = activityMapper.selectByDate(
            df.format(getActivityVo.getStartDate()),
            df.format(getActivityVo.getEndDate()));
        log.info("获取活动，request param : {}，活动size: {}", getActivityVo, dbActivities.size());

        // 组装下
        List<GetActivityRspVo> activities = new ArrayList<>();
        for (Activity activity : dbActivities) {
            GetActivityRspVo rspVo = getActivityRspVo(activity);
            activities.add(rspVo);
        }

        // 按分类组装
        Map<String, List<GetActivityRspVo>> map = activities.stream()
            .collect(Collectors.groupingBy(GetActivityRspVo::getStartDate));
        TreeMap<String, List<GetActivityRspVo>> map2 = new TreeMap<>(map);

        List<GetActivityByDateRspVo> rsp = new ArrayList<>();
        for (Map.Entry<String, List<GetActivityRspVo>> entry : map2.entrySet()) {
            GetActivityByDateRspVo vo = new GetActivityByDateRspVo();
            vo.setDate(entry.getKey());
            vo.setList(entry.getValue());
            rsp.add(vo);
        }
        return rsp;
    }

    private GetActivityRspVo getActivityRspVo(Activity activity) {
        GetActivityRspVo rspVo = new GetActivityRspVo();
        BeanUtils.copyProperties(activity, rspVo);


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

        // 给前端 dateRange 和 timeRange
        rspVo.setDateRange(rspVo.getStartDate() + " - " + rspVo.getEndDate());
        rspVo.setTimeRange(activity.getStartTime() + " - " + activity.getEndTime());

        return rspVo;
    }
}
