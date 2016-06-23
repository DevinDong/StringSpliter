package com.ctrip.stringspliter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 字符串拆分合并类
 **/
public class StringSpliter {

    class StringSplitPOJO {

	private String messageId;
	// 分子
	private int numerator;
	// 分母
	private int denominator;
	// 值
	private String value;

	public String getMessageId() {
	    return messageId;
	}

	public void setMessageId(String messageId) {
	    this.messageId = messageId;
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
	    return String.format("%s-%s/%s:%s", this.messageId, this.numerator, this.denominator, this.value);
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
    public List<String> split(String input, int size) {
	if (size <= 0) {
	    return new ArrayList<>();
	}
	String id = UUID.randomUUID().toString().replace("-", "");
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
	    dto.setMessageId(id);
	    dto.setValue(value);
	    dto.setNumerator(i + 1);
	    dto.setDenominator(count);
	    String formatValue = String.format("%s-%s/%s:%s", dto.getMessageId(), dto.getNumerator(), dto.getDenominator(), dto.getValue());
	    ret.add(formatValue);
	}
	return ret;
    }

    /**
     * 合并字符串
     **/
    public List<String> merge(List<String> inputList) {
	if (inputList == null || inputList.isEmpty()) {
	    return new ArrayList<>();
	}
	List<StringSplitPOJO> pojos = convertToSplitDtos(inputList);
	List<String> retList = stringSplitPOJOToStringList(pojos);
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
	    dto.setMessageId(arraysHeader[0]);
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

    private List<String> stringSplitPOJOToStringList(List<StringSplitPOJO> pojos) {
	List<String> retList = new ArrayList<>();
	Set<String> groups = new HashSet<>();
	for (StringSplitPOJO pojo : pojos) {
	    if (!groups.contains(pojo.getMessageId())) {
		groups.add(pojo.getMessageId());
	    }
	}
	Iterator<String> iterator = groups.iterator();
	while (iterator.hasNext()) {
	    String group = (String) iterator.next();
	    List<StringSplitPOJO> itemPojos = new ArrayList<>();
	    for (StringSplitPOJO pojo : pojos) {
		if (group.equals(pojo.getMessageId())) {
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
		retList.add(sb.toString());
	    } else {
		throw new IllegalArgumentException("消息不完整.messageId:" + itemPojos.get(0).getMessageId());
	    }
	}
	return retList;
    }

}
