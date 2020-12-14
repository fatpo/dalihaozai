package com.dalish.wx.miniapp.controller;

import com.dalish.wx.miniapp.entity.Activity;
import com.dalish.wx.miniapp.service.ActivityService;
import com.dalish.wx.miniapp.utils.ReturnCode;
import com.dalish.wx.miniapp.utils.ReturnObj;
import com.dalish.wx.miniapp.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/activity")
@Slf4j
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping("/insert")
    public ReturnObj<InsertActivityRspVo> insertActivity(@Valid @RequestBody InsertActivityVo activityVo, BindingResult bindingResult) {
        long startTime = System.currentTimeMillis();
        log.info("新增活动入参：{}", activityVo);

        if (null != bindingResult && bindingResult.hasErrors()) {
            List<String> msg = bindingResult.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
            log.error("新增活动入参异常：{}", msg);
            return new ReturnObj<>(ReturnCode.PARAM_ERROR.getCode(), msg.toString());
        }
        try {
            Activity activity = new Activity();
            BeanUtils.copyProperties(activityVo, activity);

            // 做一些额外的属性处理
            if (activity.getPrice() == null) {
                activity.setPrice(0);
            }

            int recordId = activityService.insertActivity(activity);
            if (recordId == -1) {
                return new ReturnObj<>(ReturnCode.BUSINESS_ERROR);
            } else {
                InsertActivityRspVo rspVo = new InsertActivityRspVo();
                BeanUtils.copyProperties(activity, rspVo);
                return new ReturnObj<>(ReturnCode.SUCCESS, rspVo);
            }
        } catch (Exception ex) {
            log.error("新增活动异常 {} ", activityVo, ex);
            return new ReturnObj<>(ReturnCode.SERVER_ERROR);
        } finally {
            log.info("新增活动耗时: {}ms", System.currentTimeMillis() - startTime);
        }
    }


    @PostMapping("/update")
    public ReturnObj<Void> updateActivity(@Valid @RequestBody UpdateActivityVo activityVo, BindingResult bindingResult) {
        long startTime = System.currentTimeMillis();
        log.info("更新活动入参：{}", activityVo);

        if (null != bindingResult && bindingResult.hasErrors()) {
            List<String> msg = bindingResult.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
            log.error("更新活动入参异常：{}", msg);
            return new ReturnObj<>(ReturnCode.PARAM_ERROR.getCode(), msg.toString());
        }
        try {
            Activity activity = new Activity();
            BeanUtils.copyProperties(activityVo, activity);

            if (activityService.updateActivity(activity)) {
                return new ReturnObj<>(ReturnCode.SUCCESS);
            } else {
                return new ReturnObj<>(ReturnCode.BUSINESS_ERROR);
            }
        } catch (Exception ex) {
            log.error("更新活动异常 {} ", activityVo, ex);
            return new ReturnObj<>(ReturnCode.SERVER_ERROR);
        } finally {
            log.info("更新活动耗时: {}ms", System.currentTimeMillis() - startTime);
        }
    }


    @PostMapping("/delete")
    public ReturnObj<Void> deleteActivity(@Valid @RequestBody DeleteActivityVo activityVo, BindingResult bindingResult) {
        long startTime = System.currentTimeMillis();
        log.info("删除活动入参：{}", activityVo);

        if (null != bindingResult && bindingResult.hasErrors()) {
            List<String> msg = bindingResult.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
            log.error("删除活动入参异常：{}", msg);
            return new ReturnObj<>(ReturnCode.PARAM_ERROR.getCode(), msg.toString());
        }
        try {
            activityService.deleteActivity(activityVo.getId());
            return new ReturnObj<>(ReturnCode.SUCCESS);
        } catch (Exception ex) {
            log.error("删除活动异常 {} ", activityVo, ex);
            return new ReturnObj<>(ReturnCode.SERVER_ERROR);
        } finally {
            log.info("删除活动耗时: {}ms", System.currentTimeMillis() - startTime);
        }
    }

    @PostMapping("/getByDate")
    public ReturnObj<List<GetActivityByDateRspVo>> getActivity(@Valid @RequestBody GetActivityByDateVo activityVo, BindingResult bindingResult) {
        long startTime = System.currentTimeMillis();
        log.info("根据日期区间获取活动入参：{}", activityVo);

        if (null != bindingResult && bindingResult.hasErrors()) {
            List<String> msg = bindingResult.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
            log.error("根据日期区间获取活动入参异常：{}", msg);
            return new ReturnObj<>(ReturnCode.PARAM_ERROR.getCode(), msg.toString());
        }
        try {
            List<GetActivityByDateRspVo> activities = activityService.getActivityByDate(activityVo);
            return new ReturnObj<>(ReturnCode.SUCCESS, activities);
        } catch (Exception ex) {
            log.error("根据日期区间获取活动异常 {} ", activityVo, ex);
            return new ReturnObj<>(ReturnCode.SERVER_ERROR);
        } finally {
            log.info("根据日期区间获取活动耗时: {}ms", System.currentTimeMillis() - startTime);
        }
    }

    @PostMapping("/get")
    public ReturnObj<List<GetActivityRspVo>> getActivity(@Valid @RequestBody GetActivityVo activityVo, BindingResult bindingResult) {
        long startTime = System.currentTimeMillis();
        log.info("获取活动入参：{}", activityVo);

        if (null != bindingResult && bindingResult.hasErrors()) {
            List<String> msg = bindingResult.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
            log.error("获取活动入参异常：{}", msg);
            return new ReturnObj<>(ReturnCode.PARAM_ERROR.getCode(), msg.toString());
        }
        try {
            List<GetActivityRspVo> activities = activityService.getActivity(activityVo);
            return new ReturnObj<>(ReturnCode.SUCCESS, activities);
        } catch (Exception ex) {
            log.error("获取活动异常 {} ", activityVo, ex);
            return new ReturnObj<>(ReturnCode.SERVER_ERROR);
        } finally {
            log.info("获取活动耗时: {}ms", System.currentTimeMillis() - startTime);
        }
    }
}
