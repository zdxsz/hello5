package com.atguigu.scw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;

@Api(tags="用户实名审核模块")
@RequestMapping("/user/auth/")
@RestController
public class UserRealAuthController {

	@ApiOperation(value="认证申请第二步-提交基本信息")
	@PostMapping("baseinfo")
	public String baseinfo() {
		return "";
	}
	
	@ApiOperation(value="认证申请第三步-上传咨询信息")
	@PostMapping("certs")
	public String certs() {
		return "";
	}
		
	@ApiOperation(value="获取需要上传的咨询信息")
	@GetMapping("certs2upload")
	public String certs2upload() {
		return "";
	}
	
	@ApiOperation(value="认证申请第四步-确认邮箱信息")
	@PostMapping("email")
	public String email() {
		return "";
	}
	
	@ApiOperation(value="认证申请第一步-用户认证申请开始")
	@GetMapping("start")
	public String start() {
		return "";
	}
		
	@ApiOperation(value="认证申请第五步-提交实名认证申明")
	@PostMapping("submit")
	public String submit() {
		return "";
	}
	
}
