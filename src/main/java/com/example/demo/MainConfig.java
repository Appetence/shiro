package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableCaching		//开启echach注解
@EnableWebMvc		//启动springmvc
@Configurable	//配置类
//将spring boot自带的DataSourceAutoConfiguration禁掉，因为它会读取application.properties文件的spring.datasource.*属性并自动配置单数据源
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class,scanBasePackages="com.example.demo.*")//全局扫描
@MapperScan(basePackages="com.example.demo.mapper")//scan DAO
public class MainConfig {

}
