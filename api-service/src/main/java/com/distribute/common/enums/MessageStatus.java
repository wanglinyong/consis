package com.distribute.common.enums;

/**
 * Created by Administrator on 2018/8/22.
 */
public enum MessageStatus {
	WAITING_CONFIRM("0", "待确认"),

	SENDING("1", "发送中");

	private String key;

	private String value;

	MessageStatus(String key ,String value){
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
}
