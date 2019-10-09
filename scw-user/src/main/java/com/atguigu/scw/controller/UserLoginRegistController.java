package com.atguigu.scw.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="用户登录注册模块")
@RequestMapping("/user/")
@RestController
public class UserLoginRegistController {
	
	@ApiOperation(value="用户登录")
	@PostMapping("login")
	public String login() {
		return "";
	}
	
	@ApiOperation(value="用户注册")
	@PostMapping("register")
	public String register() {
		return "";
	}
	
	@ApiOperation(value="重置密码")
	@PostMapping("reset")
	public String reset() {
		return "";
	}
	
	@ApiOperation(value="发送短信验证码")
	@PostMapping("sendsms")
	public String sendsms() {
		return "";
	}
	
	@ApiOperation(value="验证短信验证码")
	@PostMapping("valide")
	public String valide() {
		return "";
	}
	
	
}
