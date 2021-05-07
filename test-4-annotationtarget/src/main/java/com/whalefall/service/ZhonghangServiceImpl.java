package com.whalefall.service;

import com.whalefall.annotation.InvokeMethod;
import com.whalefall.domain.Info;
import org.springframework.stereotype.Component;

/**
 * @Author: WhaleFall541
 * @Date: 2020/9/9 21:26
 */
@Component("zhonghang")
public class ZhonghangServiceImpl implements IService{

    @Override
    public String query(Info info) {
        return "zhonghang query result";
    }

    @InvokeMethod("A003")
    public String update() {
        return "zhonghang update";
    }
}
