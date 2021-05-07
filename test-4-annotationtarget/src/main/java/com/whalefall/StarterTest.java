package com.whalefall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: WhaleFall541
 * @Date: 2020/9/9 19:46
 */

@ComponentScan("com.whalefall.*")
@SpringBootApplication
public class StarterTest {
    public static void main(String[] args) {
        SpringApplication.run(StarterTest.class, args);
    }
}

