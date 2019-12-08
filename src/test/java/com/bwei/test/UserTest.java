package com.bwei.test;

import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bawei.cms.utils.TimeRandomUtil;
import com.bawei.cms.utils.UserRandomUtil;
import com.bwei.cms.bean.User;
@SuppressWarnings("all")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring-context.xml")
public class UserTest {

	
	@Resource
	private RedisTemplate redisTemplate;
	
	@Test
	public void userTest() {
		
		// 获取当前时间
		long timeMillis1 = System.currentTimeMillis();
		
		// string 类型
		ValueOperations ops = redisTemplate.opsForValue();
		
		// hash 类型
		BoundHashOperations hashOps = redisTemplate.boundHashOps("hash_user");
		
		for(int i=1;i<=100000;i++) {
			
			User user = new User();
			// 序号
			user.setId(i);
			// 名字
			user.setName(UserRandomUtil.getChineseName());
			// 性别
			user.setSex(getSex());
			// 手机
			user.setPhone(getPhone());
			// 邮箱
			user.setEmail(UserRandomUtil.getEmail());
			// 生日
			user.setBirthday(TimeRandomUtil.randomDate("1949-01-01 00:00:00", "2001-01-01 00:00:00"));
			//System.out.println(user);
			// 存入redis中
			//ops.set(i+"", user);
			hashOps.put(i+"", user.toString());
			
		}
		long timeMillis2 = System.currentTimeMillis();
		long time=timeMillis2-timeMillis1;
		System.out.println("序列化方式：hash,耗时："+time);
		
	}
	
	public static String getSex() {
		return new Random().nextBoolean()?"男":"女";
	}
	
	public static String getPhone() {
		
		String phone = "";
		for(int i=0;i<9;i++) {
			phone+=new Random().nextInt(10);
		}
		return "13"+phone;
		
	}
	
}
