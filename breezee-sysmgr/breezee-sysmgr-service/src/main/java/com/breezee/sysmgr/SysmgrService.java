/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr;

import com.breezee.common.util.ContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

/**
 * Created by Silence on 2016/2/10.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.breezee")
@ImportResource(value = {"classpath:/sysmgr-provider.xml"})
public class SysmgrService {

    /**
     * 启动本服务
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ContextUtil.current = SpringApplication.run(SysmgrService.class, args);
    }
}
