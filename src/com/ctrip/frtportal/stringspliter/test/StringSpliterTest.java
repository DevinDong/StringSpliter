package com.ctrip.frtportal.stringspliter.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.ctrip.frtportal.stringspliter.StringSpliter;

import static org.junit.Assert.*;

public class StringSpliterTest {

    private StringSpliter spliter = new StringSpliter();

    @Test
    public void testSplit() {
	String input = "abcdefghijklmnopqrstuvwxyz";
	// case 0
	List<String> items = spliter.split("key_1", input, 10);
	assertNotNull(items);
	assertEquals(3, items.size());
	// case 1
	input = "a";
	items = spliter.split("key_2", input, 1);
	assertNotNull(items);
	assertEquals(1, items.size());
	// case 2
	items = spliter.split("key_3", input, 0);
	assertNotNull(items);
	assertEquals(0, items.size());
    }

    @Test
    public void testMerge() {
	List<String> source = new ArrayList<>();
	source.add("bbb-2/3:2_2_");
	source.add("aaa-1/3:1_1_");
	source.add("aaa-1/3:1_1_");
	source.add("aaa-3/3:1_3");
	source.add("bbb-3/3:2_3");
	source.add("bbb-3/3:2_3");
	source.add("bbb-1/3:2_1_");
	source.add("aaa-2/3:1_2_");
	Map<String, String> actual = spliter.merge(source);
	assertNotNull(actual);
	assertEquals(2, actual.size());
	assertEquals("1_1_1_2_1_3", actual.get("aaa"));
	assertEquals("2_1_2_2_2_3", actual.get("bbb"));
	source = new ArrayList<>();
	source.add("ccc-2/3:3_2_");
	source.add("ccc-1/3:3_1_");
	try {
	    actual = spliter.merge(source);
	    fail("should throw exception.");
	} catch (Exception e) {

	}
    }
}
