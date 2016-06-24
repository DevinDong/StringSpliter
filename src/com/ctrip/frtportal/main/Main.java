package com.ctrip.frtportal.main;

import java.util.List;
import java.util.Map;

import com.ctrip.frtportal.stringspliter.StringSpliter;

public class Main {
    public static void main(String[] args) {
	StringSpliter spliter = new StringSpliter();
	DemoEntity entity = new DemoEntity();

	entity.setId("111");
	entity.setName("name111");
	entity.setValue("1234567890111");

	List<String> list = spliter.split(entity.getId(), entity.getValue(), 5);
	for (String val : list) {
	    System.out.println(val);
	}

	Map<String, String> map = spliter.merge(list);
	String value = map.get(entity.getId());
	System.out.println(value);
    }
}
