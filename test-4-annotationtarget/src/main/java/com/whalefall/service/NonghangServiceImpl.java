package com.whalefall.service;

import com.whalefall.annotation.InvokeMethod;
import com.whalefall.domain.Info;
import org.springframework.stereotype.Component;

/**
 * @Author: WhaleFall541
 * @Date: 2020/9/9 21:25
 */
@Component("nonghang")
public class NonghangServiceImpl implements IService{

    @Override
    public String query(Info info) {
        return "nonghang query result";
    }

    @InvokeMethod("A003")
    public String update() {
        return "nonghang update";
    }
}
