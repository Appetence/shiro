package com.example.demo.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

public class HashSatlUtil {
	public static Map<String, Object> HashSaltUtil(String method,String pass,int times){
		Map<String, Object> map = new HashMap<>();
		String salt = new SecureRandomNumberGenerator().nextBytes().toString();
		String encodedPassword = new SimpleHash(method, pass, salt, times).toString();
		map.put("salt", salt);
		map.put("encodedPassword", encodedPassword);
		return map;
	}
}
