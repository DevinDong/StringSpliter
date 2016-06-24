package com.ctrip.stringspliter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 字符串拆分合并类
 **/
public class StringSpliter {

    class StringSplitPOJO {

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

    class StringSplitPOJOComparator implements Comparator<StringSplitPOJO> {
	@Override
	public int compare(StringSplitPOJO o1, StringSplitPOJO o2) {
	    return o1.getNumerator() - o2.getNumerator();
	}
    }

    /**
     * 拆分字符串
     **/
    public List<String> split(String key, String input, int size) {
	if (size <= 0) {
	    return new ArrayList<>();
	}
	double d = (double) input.length() / (double) size;
	int count = (int) Math.ceil(d);
	List<String> ret = new ArrayList<>(count);
	for (int i = 0; i < count; i++) {
	    StringSplitPOJO dto = new StringSplitPOJO();
	    String value = "";
	    for (int j = 0; j < size; j++) {
		int index = i * size + j;
		if (index < input.length()) {
		    value += input.charAt(index);
		}
	    }
	    dto.setMessageKey(key);
	    dto.setValue(value);
	    dto.setNumerator(i + 1);
	    dto.setDenominator(count);
	    String formatValue = String.format("%s-%s/%s:%s", dto.getMessageKey(), dto.getNumerator(), dto.getDenominator(), dto.getValue());
	    ret.add(formatValue);
	}
	return ret;
    }

    /**
     * 合并字符串
     **/
    public Map<String, String> merge(List<String> inputList) {
	if (inputList == null || inputList.isEmpty()) {
	    return new HashMap<>();
	}
	List<StringSplitPOJO> pojos = convertToSplitDtos(inputList);
	Map<String, String> retList = stringSplitPOJOToStringList(pojos);
	return retList;
    }

    private List<StringSplitPOJO> convertToSplitDtos(List<String> inputList) {
	List<StringSplitPOJO> dtoList = new ArrayList<>(inputList.size());
	for (String input : inputList) {
	    String[] arrays = input.split(":");
	    if (arrays.length <= 1) {
		throw new IllegalArgumentException();
	    }
	    StringSplitPOJO dto = new StringSplitPOJO();
	    String header = arrays[0];
	    StringBuilder value = new StringBuilder();
	    for (int i = 1; i < arrays.length; i++) {
		value.append(arrays[i]);
	    }
	    dto.setValue(value.toString());
	    String[] arraysHeader = header.split("-");
	    if (arrays.length != 2) {
		throw new IllegalArgumentException();
	    }
	    dto.setMessageKey(arraysHeader[0]);
	    String[] fractionArray = arraysHeader[1].split("/");
	    if (fractionArray.length != 2) {
		throw new IllegalArgumentException();
	    }
	    dto.setNumerator(Integer.valueOf(fractionArray[0]));
	    dto.setDenominator(Integer.valueOf(fractionArray[1]));
	    dtoList.add(dto);
	}
	return dtoList;
    }

    private Map<String, String> stringSplitPOJOToStringList(List<StringSplitPOJO> pojos) {
	Map<String, String> retMap = new HashMap<>();
	Set<String> groups = new HashSet<>();
	for (StringSplitPOJO pojo : pojos) {
	    if (!groups.contains(pojo.getMessageKey())) {
		groups.add(pojo.getMessageKey());
	    }
	}
	Iterator<String> iterator = groups.iterator();
	while (iterator.hasNext()) {
	    String group = (String) iterator.next();
	    List<StringSplitPOJO> itemPojos = new ArrayList<>();
	    for (StringSplitPOJO pojo : pojos) {
		if (group.equals(pojo.getMessageKey())) {
		    itemPojos.add(pojo);
		}
	    }
	    Collections.sort(itemPojos, new StringSplitPOJOComparator());
	    StringBuilder sb = new StringBuilder();
	    int count = 0;
	    Set<Integer> numerators = new HashSet<>();
	    for (StringSplitPOJO pojo : itemPojos) {
		if (!numerators.contains(pojo.getNumerator())) {
		    numerators.add(pojo.getNumerator());
		    sb.append(pojo.getValue());
		    count++;
		}
	    }
	    // check if complete
	    if (count == itemPojos.get(0).getDenominator()) {
		retMap.put(itemPojos.get(0).getMessageKey(), sb.toString());
	    } else {
		throw new IllegalArgumentException("消息不完整.MessageKey:" + itemPojos.get(0).getMessageKey());
	    }
	}
	return retMap;
    }

}
