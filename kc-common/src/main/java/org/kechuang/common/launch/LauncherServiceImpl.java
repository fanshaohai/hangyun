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
package org.kechuang.common.launch;

import org.kechuang.common.constant.LauncherConstant;
import org.kechuang.core.auto.service.AutoService;
import org.kechuang.core.launch.service.LauncherService;
import org.kechuang.core.launch.utils.PropsUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Properties;

/**
 * 启动参数拓展
 *
 * @author smallchil
 */
@AutoService(LauncherService.class)
public class LauncherServiceImpl implements LauncherService {

	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {
		Properties props = System.getProperties();
		// 通用注册
		PropsUtil.setProperty(props,"spring.cloud.nacos.discovery.namespace",LauncherConstant.nacosNameSpace(profile));
		PropsUtil.setProperty(props,"spring.cloud.nacos.config.namespace",LauncherConstant.nacosNameSpace(profile));
		PropsUtil.setProperty(props, "spring.cloud.nacos.discovery.server-addr", LauncherConstant.nacosAddr(profile));
		PropsUtil.setProperty(props, "spring.cloud.nacos.config.server-addr", LauncherConstant.nacosAddr(profile));
		PropsUtil.setProperty(props, "spring.cloud.sentinel.transport.dashboard", LauncherConstant.sentinelAddr(profile));
//		// dubbo注册
//		PropsUtil.setProperty(props, "dubbo.application.name", appName);
//		PropsUtil.setProperty(props, "dubbo.application.qos.enable", "false");
//		PropsUtil.setProperty(props, "dubbo.protocol.name", "dubbo");
//		PropsUtil.setProperty(props, "dubbo.registry.address", "nacos://" + LauncherConstant.nacosAddr(profile));
//		PropsUtil.setProperty(props, "dubbo.version", AppConstant.APPLICATION_VERSION);
//		PropsUtil.setProperty(props, "dubbo.scan.base-packages", AppConstant.BASE_PACKAGES);
	}

}
