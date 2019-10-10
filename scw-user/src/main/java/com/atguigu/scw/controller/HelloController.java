package com.atguigu.scw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.scw.bean.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags="测试Swagger的Controller")
@RestController
public class HelloController {

	@ApiOperation(value="hello方法")
	@RequestMapping("/hello")
	public String hello() {
		return "hhhhhh";
	}
	
	@ApiOperation(value="登录方法")
	@ApiImplicitParams(value= {
			@ApiImplicitParam(name="username",required=true,dataTypeClass=String.class),
			@ApiImplicitParam(name="pwd",required=false,defaultValue="123123")
	})
	@RequestMapping("/login")
	public User login(String username,String pwd) {
		return new User(500, username, pwd);
	}
	
	public void zxc(){
		//clone实验
	}
}
