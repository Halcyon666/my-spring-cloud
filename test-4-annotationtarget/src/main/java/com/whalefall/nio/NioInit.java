package com.whalefall.nio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

//@Component
public class NioInit implements ApplicationRunner {

    @Autowired
    private NIOServer nioServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            nioServer.service();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
