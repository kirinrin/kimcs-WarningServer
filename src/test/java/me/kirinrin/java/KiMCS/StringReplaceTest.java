package me.kirinrin.java.KiMCS;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringReplaceTest {

	@Test
	public void test() {
		String jsonString = "{name=蜗牛, timestamp=14:01:38, msg=INIT10.200.0.144}";
		jsonString = jsonString.replaceAll("10.200.0.", "-");
		assertTrue(jsonString.equals("{name=蜗牛, timestamp=14:01:38, msg=INIT-144}"));
	}

}
