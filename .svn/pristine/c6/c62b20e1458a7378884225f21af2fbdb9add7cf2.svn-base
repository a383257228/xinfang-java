package com.sinosoft.xf.util;

import java.util.UUID;

/**
 * UUID生成器
 */
public class UUIDGenerator {
	public static String generate36() {
		return UUID.randomUUID().toString();
	}
	public static String generate32() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	public static String generate20() {
		return generate32().substring(0,20);
	}
	public static String generate26() {
		return generate32().substring(0,26);
	}
}
