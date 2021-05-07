package com.whalefall.service;

import com.whalefall.domain.Info;
import org.springframework.stereotype.Component;

/**
 * @Author: WhaleFall541
 * @Date: 2020/9/9 23:05
 */

public interface IService {
    public String query(Info info);
    public String update();
}
