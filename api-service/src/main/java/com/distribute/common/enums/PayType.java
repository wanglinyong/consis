package com.distribute.common.enums;

public enum PayType {
    WXPAY("1", "微信公众号支付"), ALIPAY("2", "支付宝支付"), WXAPPPAY("3", "微信app支付");

    private String payKey;
    private String payName;

    PayType(String payKey, String payName) {
        this.payKey = payKey;
        this.payName = payName;
    }

    public String getPayKey() {
        return payKey;
    }

    public String getPayName() {
        return payName;
    }

    public static PayType getPayType(String payKey) {
        PayType type = null;
        for (PayType payType : PayType.values()) {
            if (payType.getPayKey().equals(payKey)) {
                type = payType;
            }
        }
        return type;
    }
}
