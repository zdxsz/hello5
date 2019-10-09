package com.atguigu.scw.controller;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.atguigu.scw.common.consts.AppConsts;
import com.atguigu.scw.common.templates.SmsTemplate;
import com.atguigu.scw.common.utils.AppResponse;
import com.atguigu.scw.common.utils.ScwUtils;
import com.atguigu.scw.user.bean.TMember;
import com.atguigu.scw.user.bean.TMemberAddress;
import com.atguigu.scw.user.service.MemberService;
import com.atguigu.scw.user.vo.request.MemberRequestVo;
import com.atguigu.scw.user.vo.response.MemberResponseVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags="处理用户注册验证码登录请求的controller")
@RestController
@Slf4j
public class UserController {

	@Autowired
	StringRedisTemplate stringRedisTemplate;
	@Autowired
	SmsTemplate smsTemplate;
	@Autowired
	MemberService memberService;
	//处理用户查询自己收获地址的请求方法
		@ApiOperation("处理用户查询自己收获地址的请求方法")
		@PostMapping("/user/info/address")
		public AppResponse<List<TMemberAddress>> addressInfo(@RequestParam("accessToken")String accessToken) {
			//根据accessToken查询用户的登录信息
			String memberStr = stringRedisTemplate.opsForValue().get(accessToken);
			if(StringUtils.isEmpty(memberStr)) {
				return AppResponse.fail(null, "登录超时，请重新登录");
			}
			//将memberStr转为member对象
			TMember tMember = JSON.parseObject(memberStr,TMember.class);
			List<TMemberAddress> address = memberService.getAllAdress(tMember.getId());
			return AppResponse.ok(address, "地址信息查询成功");
		}
	//2、处理注册请求的方法
	@ApiOperation("处理注册请求的方法")
	@PostMapping("/user/doRegist")
	public AppResponse<Object> doRegist(MemberRequestVo vo) {
		String loginacct = vo.getLoginacct();
		String redisCode = stringRedisTemplate.opsForValue().get(AppConsts.CODE_PREFIX+loginacct+AppConsts.CODE_CODE_SUFFIX);
		if(StringUtils.isEmpty(redisCode)) {
			return AppResponse.fail(null, "验证码过期");
		}
		if(!redisCode.equals(vo.getCode())) {
			return AppResponse.fail(null, "验证码错误");
		}
		//注册
		memberService.saveMember(vo);
		//删除验证码
		stringRedisTemplate.delete(AppConsts.CODE_PREFIX+loginacct+AppConsts.CODE_CODE_SUFFIX);
		return AppResponse.ok(null, "注册成功");
	}
	
	
	//3、处理登录请求
	@ApiOperation("处理登录请求")
	@PostMapping("/user/doLogin")
	public AppResponse<Object> doLogin(@RequestParam("loginacct")String loginacct,@RequestParam("userpswd")String userpswd){
		//调用Service的方法查询用户信息
		TMember member = memberService.getMemeber(loginacct,userpswd);
		if(member==null) {
			return AppResponse.fail(null, "登录失败！账号或密码错误");
		}
		//查询成功，创建存储信息的键，将用户信息存到redis中
		String memberKey = UUID.randomUUID().toString().replace("-", "");
		String memberJson = JSON.toJSONString(member);
		stringRedisTemplate.opsForValue().set(memberKey, memberJson, 7, TimeUnit.DAYS);
		//返回token给前台系统
		//响应用户信息给前台项目，可以封装对应的vo
		MemberResponseVo responseVo = new MemberResponseVo();
		BeanUtils.copyProperties(member, responseVo);
		responseVo.setAccessToken(memberKey);
		
		return AppResponse.ok(responseVo, "登录成功");
	}
	
	//1、给手机号码发送短信验证码的方法
	@ApiOperation("发送验证码的方法")
	@ApiImplicitParams(value= @ApiImplicitParam(name="phoneNum",required=true,value="手机号码"))
	@PostMapping("/user/sendSms")
	public AppResponse<Object> sendSms(@RequestParam("phoneNum")String phoneNum) {
		//验证手机号码格式
		boolean b = ScwUtils.isMobilePhone(phoneNum);
		if(!b) {
			return AppResponse.fail(null, "手机号码格式错误");
		}
		//验证redis中存储的当前手机号获取验证码的次数，一天最多3次，code:123456:count
		String countStr = stringRedisTemplate.opsForValue().get(AppConsts.CODE_PREFIX+phoneNum+AppConsts.CODE_COUNT_SUFFIX);
		int count = 0;
		if(!StringUtils.isEmpty(countStr)) {
			count = Integer.parseInt(countStr);
		}
		if(count>=3) {
			return AppResponse.fail(null, "验证码次数超标");
		}
		//验证redis中是否存在未过期验证码，code:3:code
		Boolean hasKey = stringRedisTemplate.hasKey(AppConsts.CODE_PREFIX+phoneNum+AppConsts.CODE_CODE_SUFFIX);
		if(hasKey) {
			return AppResponse.fail(null, "请不要频繁获取验证码");
		}
		//发送验证码
		//随机生成验证码
		String code = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
	
		//封装发送短信验证码请求参数的集合
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("mobile", phoneNum);
	    querys.put("param", AppConsts.CODE_PREFIX+code);
	   
	    querys.put("tpl_id", "TP1711063");
	    //	发送短信效果，自己注释的######
//	    boolean sendSms = smsTemplate.sendSms(querys);
//	    if(!sendSms) {
//	    return AppResponse.fail(null, "短信验证码发送失败");
//	    	return "短信验证码发送失败";
//	    }
	    //将验证码存到redis中5分钟
	    stringRedisTemplate.opsForValue().set(AppConsts.CODE_PREFIX+phoneNum+AppConsts.CODE_CODE_SUFFIX, code, 5, TimeUnit.MINUTES);
		Long expire = stringRedisTemplate.getExpire(AppConsts.CODE_PREFIX+phoneNum+AppConsts.CODE_COUNT_SUFFIX,TimeUnit.MINUTES);
		log.info("查询到的过期时间:{}", expire);
		if(expire<0) {
			expire=(long) (24*60);
		}
		stringRedisTemplate.opsForValue().set(AppConsts.CODE_PREFIX+phoneNum+AppConsts.CODE_COUNT_SUFFIX, ++count+"", expire, TimeUnit.MINUTES);
	    return AppResponse.ok(null, "发送验证成功");
	}
	
	

	
	
}
