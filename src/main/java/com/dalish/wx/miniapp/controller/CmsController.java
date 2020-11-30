package com.dalish.wx.miniapp.controller;

import com.dalish.wx.miniapp.utils.ReturnCode;
import com.dalish.wx.miniapp.utils.ReturnObj;
import com.dalish.wx.miniapp.vo.LoginRspVo;
import com.dalish.wx.miniapp.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/cms")
@Slf4j
public class CmsController {

    @Value("${cms.login.name:admin}")
    String name;

    @Value("${cms.login.password:46f94c8de14fb36680850768ff1b7f2a}")
    String password;

    @PostMapping("/login")
    public ReturnObj<LoginRspVo> login(@Valid @RequestBody LoginVo loginVo, BindingResult bindingResult) {
        long startTime = System.currentTimeMillis();
        log.info("登录入参：{}", loginVo);

        if (null != bindingResult && bindingResult.hasErrors()) {
            List<String> msg = bindingResult.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
            log.error("登录入参异常：{}", msg);
            return new ReturnObj<>(ReturnCode.PARAM_ERROR.getCode(), msg.toString());
        }
        try {
            String loginName = loginVo.getName();
            String password = loginVo.getPassword();
            if (loginName.equals(this.name) && password.equals(this.password)) {
                LoginRspVo rspVo = new LoginRspVo();
                rspVo.setToken("djfakdjalkfndke1@#!@sdjlk");
                rspVo.setName(name);
                return new ReturnObj<>(ReturnCode.SUCCESS, rspVo);
            }
            log.info("登录 {}, {} 密码错误!", name, password);
            return new ReturnObj<>(ReturnCode.PASSWORD_ERROR);
        } catch (Exception ex) {
            log.error("登录异常 {} ", loginVo, ex);
            return new ReturnObj<>(ReturnCode.SERVER_ERROR);
        } finally {
            log.info("登录活动耗时: {}ms", System.currentTimeMillis() - startTime);
        }
    }

}
