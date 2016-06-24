package com.ctrip.frtportal.stringspliter;

public class StringSplitPOJO {

    private String messageKey;
    // 分子
    private int numerator;
    // 分母
    private int denominator;
    // 值
    private String value;

    public String getMessageKey() {
	return messageKey;
    }

    public void setMessageKey(String msgKey) {
	this.messageKey = msgKey;
    }

    public int getNumerator() {
	return numerator;
    }

    public void setNumerator(int numerator) {
	this.numerator = numerator;
    }

    public int getDenominator() {
	return denominator;
    }

    public void setDenominator(int denominator) {
	this.denominator = denominator;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    @Override
    public String toString() {
	return String.format("%s-%s/%s:%s", this.messageKey, this.numerator, this.denominator, this.value);
    }
}