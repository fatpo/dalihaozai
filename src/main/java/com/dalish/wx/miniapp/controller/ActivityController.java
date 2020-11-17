package com.dalish.wx.miniapp.controller;

import com.dalish.wx.miniapp.entity.Activity;
import com.dalish.wx.miniapp.vo.DeleteActivityVo;
import com.dalish.wx.miniapp.vo.SyncActivityVo;
import com.dalish.wx.miniapp.service.ActivityService;
import com.dalish.wx.miniapp.utils.ReturnCode;
import com.dalish.wx.miniapp.utils.ReturnObj;
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

/**
 * <pre>
 *  小程序临时素材接口
 *  Created by BinaryWang on 2017/6/16.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@RestController
@RequestMapping("/activity")
@Slf4j
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping("/sync")
    public ReturnObj<Void> syncActivity(@Valid @RequestBody SyncActivityVo activityVo, BindingResult bindingResult) {
        long startTime = System.currentTimeMillis();
        log.info("活动同步入参：{}", activityVo);

        if (null != bindingResult && bindingResult.hasErrors()) {
            List<String> msg = bindingResult.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
            log.error("同步活动入参异常：{}", msg);
            return new ReturnObj<>(ReturnCode.PARAM_ERROR.getCode(), msg.toString());
        }
        try {
            Activity activity = new Activity();
            BeanUtils.copyProperties(activityVo, activity);

            activityService.syncActivity(activity);
            return new ReturnObj<>(ReturnCode.SUCCESS);
        }catch (Exception ex){
            log.error("同步活动异常 {} ", activityVo, ex);
            return new ReturnObj<>(ReturnCode.SERVER_ERROR);
        }finally {
            log.info("同步活动耗时: {}ms", System.currentTimeMillis() - startTime);
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
            activityService.deleteActivity(activityVo.getTitle());
            return new ReturnObj<>(ReturnCode.SUCCESS);
        }catch (Exception ex){
            log.error("删除活动异常 {} ", activityVo, ex);
            return new ReturnObj<>(ReturnCode.SERVER_ERROR);
        }finally {
            log.info("删除活动耗时: {}ms", System.currentTimeMillis() - startTime);
        }
    }
}
