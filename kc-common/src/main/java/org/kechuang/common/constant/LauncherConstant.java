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
package org.kechuang.common.constant;

import org.kechuang.core.launch.constant.AppConstant;

import static org.kechuang.core.launch.constant.AppConstant.APPLICATION_NAME_PREFIX;

/**
 * 通用常量
 *
 * @author kc
 */
public interface LauncherConstant {

	/**
	 * DEV nacos namespace id
	 */
	String NACOS_DEV_NAMESPACE = "bb51d4a2-d0e9-41b8-9b00-c9856540baf5";

	/**
	 * TEST nacos namespace id
	 */
	String NACOS_TEST_NAMESPACE = "bb51d4a2-d0e9-41b8-9b00-c9856540baf5";

	/**
	 * PROD nacos namespace id
	 */
	String NACOS_PROD_NAMESPACE = "bb51d4a2-d0e9-41b8-9b00-c9856540baf5";

	/**
	 * nacos dev 地址
	 */
	String NACOS_DEV_ADDR = "127.0.0.1:8848";

	/**
	 * nacos prod 地址
	 */
	String NACOS_PROD_ADDR = "192.168.1.191:8848";

	/**
	 * nacos test 地址
	 */
	String NACOS_TEST_ADDR = "192.168.1.191:8848";

	/**
	 * sentinel dev 地址
	 */
	String SENTINEL_DEV_ADDR = "192.168.1.191:8849";

	/**
	 * sentinel prod 地址
	 */
	String SENTINEL_PROD_ADDR = "192.168.1.191:8849";

	/**
	 * sentinel test 地址
	 */
	String SENTINEL_TEST_ADDR = "192.168.1.191:8849";

	/**
	 * dbuuo提供者
	 */
	String APPLICATION_DUBBO_PROVIDER_NAME = APPLICATION_NAME_PREFIX + "dubbo-provider";

	/**
	 * dbuuo消费者
	 */
	String APPLICATION_DUBBO_CONSUMER_NAME = APPLICATION_NAME_PREFIX + "dubbo-consumer";

	/**
	 * seata订单
	 */
	String APPLICATION_SEATA_ORDER_NAME = APPLICATION_NAME_PREFIX + "seata-order";

	/**
	 * seata库存
	 */
	String APPLICATION_SEATA_STORAGE_NAME = APPLICATION_NAME_PREFIX + "seata-storage";

	/**
	 * easypoi
	 */
	String APPLICATION_EASYPOI_NAME = APPLICATION_NAME_PREFIX + "easypoi";

	/**
	 * kafka
	 */
	String APPLICATION_KAFKA_NAME = APPLICATION_NAME_PREFIX + "kafka";

	/**
	 * rabbit
	 */
	String APPLICATION_RABBIT_NAME = APPLICATION_NAME_PREFIX + "rabbit";

	/**
	 * stream消费者
	 */
	String APPLICATION_STREAM_CONSUMER_NAME = APPLICATION_NAME_PREFIX + "stream-consumer";

	/**
	 * stream生产者
	 */
	String APPLICATION_STREAM_PROVIDER_NAME = APPLICATION_NAME_PREFIX + "stream-provider";

	/**
	 * 动态获取nacos地址
	 *
	 * @param profile 环境变量
	 * @return addr
	 */
	static String nacosAddr(String profile) {
		switch (profile) {
			case (AppConstant.PROD_CODE):
				return NACOS_PROD_ADDR;
			case (AppConstant.TEST_CODE):
				return NACOS_TEST_ADDR;
			default:
				return NACOS_DEV_ADDR;
		}
	}

	/**
	 * 动态获取nacos命名空间
	 *
	 * @param profile 环境变量
	 * @return addr
	 */
	static String nacosNameSpace(String profile) {
		switch (profile) {
			case (AppConstant.PROD_CODE):
				return NACOS_PROD_NAMESPACE;
			case (AppConstant.TEST_CODE):
				return NACOS_TEST_NAMESPACE;
			default:
				return NACOS_DEV_NAMESPACE;
		}
	}

	/**
	 * 动态获取sentinel地址
	 *
	 * @param profile 环境变量
	 * @return addr
	 */
	static String sentinelAddr(String profile) {
		switch (profile) {
			case (AppConstant.PROD_CODE):
				return SENTINEL_PROD_ADDR;
			case (AppConstant.TEST_CODE):
				return SENTINEL_TEST_ADDR;
			default:
				return SENTINEL_DEV_ADDR;
		}
	}

}
