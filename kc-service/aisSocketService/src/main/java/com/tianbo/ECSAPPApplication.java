/*
 *      Copyright (c) 2016-2026, KC All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: kc (admin@kechung.net)
 */
package com.tianbo;

import com.tianbo.common.interceptor.HttpInterceptor;
import com.tianbo.launcher.SocketThread;
import org.kechuang.common.constant.CommonConstant;
import org.kechuang.core.launch.KcApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Demo启动器
 *
 * @author kc
 */
@EnableFeignClients
@SpringCloudApplication
public class ECSAPPApplication  implements WebMvcConfigurer {

	private static ApplicationContext applicationContext;
	public static void main(String[] args) {
		KcApplication.run(CommonConstant.AppName, ECSAPPApplication.class, args);
		new SocketThread().start();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public HttpInterceptor getHttpInterceptor(){
		return new HttpInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getHttpInterceptor()).addPathPatterns("/enterpriseApi/ecs/**");
	}
}

