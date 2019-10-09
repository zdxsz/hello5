package com.atguigu.scw;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.antlr.stringtemplate.StringTemplate;
import org.apache.http.HttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.atguigu.scw.common.templates.SmsTemplate;
import com.atguigu.scw.common.utils.HttpUtils;
import com.atguigu.scw.user.bean.TMember;
import com.atguigu.scw.user.mapper.TMemberMapper;
import com.google.gson.Gson;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ScwUserApplicationTests {
	
//	@Autowired
//	RedisTemplate<Object, Object> redisTemplate; //操作redis的模板类，一般用来操作对象
//	@Autowired
//	StringRedisTemplate stringTemplate;	//操作redis的模板类，一般操作字符串
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	SmsTemplate smsTemplate;
	
	@Test
	public void contextLoads() {
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("mobile", "13858164468");
	    querys.put("param", "code:1234");
	    querys.put("tpl_id", "TP1711063");
		smsTemplate.sendSms(querys);
//		Boolean flag = redisTemplate.hasKey("k1");
//		logger.info("redis中key1键是否存在：{}",flag);
//		redisTemplate.opsForValue().set("k1", "坑爹Redis包", 100, TimeUnit.SECONDS);
//		Long expire = redisTemplate.getExpire("k1",  TimeUnit.SECONDS);
//		logger.info("redis中key1键的过期时间：{}",expire);
		//redisTemplate.delete("k1");
	
//		stringTemplate.opsForValue().set("k2","code:1234567",5,TimeUnit.HOURS);
//		Date date = new Date();
//		Gson gson = new Gson();
//		String json = gson.toJson(date);
//		stringTemplate.opsForValue().set("dataStr",json);
//		String dataStr = stringTemplate.opsForValue().get("dataStr");
//		date = gson.fromJson(json, Date.class);
//		System.out.println(date);
	
		//测试短信平台
//		   String host = "http://dingxin.market.alicloudapi.com";
//		    String path = "/dx/sendSms";
//		    String method = "POST";
//		    String appcode = "3d3a5edbc6a24613b4c2e09aa1b8273011";
//		    Map<String, String> headers = new HashMap<String, String>();
//		    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
//		    headers.put("Authorization", "APPCODE " + appcode);
//		    Map<String, String> querys = new HashMap<String, String>();
//		    querys.put("mobile", "13858164468");
//		    querys.put("param", "code:1234");
//		    querys.put("tpl_id", "TP1711063");
//		    Map<String, String> bodys = new HashMap<String, String>();
//
//
//		    try {
//		    	/**
//		    	* 重要提示如下:
//		    	* HttpUtils请从
//		    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
//		    	* 下载
//		    	*
//		    	* 相应的依赖请参照
//		    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
//		    	*/
//		    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
//		    	System.out.println(response.toString());
//		    	//获取response的body
//		    	//System.out.println(EntityUtils.toString(response.getEntity()));
//		    } catch (Exception e) {
//		    	e.printStackTrace();
//		    }
//	
	}

	@Autowired
	TMemberMapper mapper;
	
	@Test
	public void testMapper() {
		List<TMember> selectByExample = mapper.selectByExample(null);
		
	}
	
}
