package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.util.SendSMSUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
		
		
		
	}
	@Test
	public void send() {
		SendSMSUtil ssu = new SendSMSUtil();
		ssu.sendSingleSMS("86","15834260040", "889977");
		
	}
	

}
