package com.whalefall.controller;

import com.whalefall.domain.Info;
import com.whalefall.service.IService;
import com.whalefall.utils.MyBeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: WhaleFall541
 * @Date: 2020/9/9 21:24
 */
@RestController
public class MethodController {

    @RequestMapping("/invokesomemtd")
    public String method1(Info info) {
        IService beansByInfo = MyBeanUtils.getBeansByInfo(info);
        return beansByInfo.query(info);
    }
}
