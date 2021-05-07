package com.whalefall.annotation;

import java.lang.annotation.*;

/**
 * @Author: WhaleFall541
 * @Date: 2020/9/9 21:06
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InvokeMethod {
    String value() default "A001";// 调用的交易类型 A001存储过程 A002查询 A003更新 A004新增
}
