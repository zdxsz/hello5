package com.atguigu.scw.user.service.Impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.atguigu.scw.user.bean.TMember;
import com.atguigu.scw.user.bean.TMemberAddress;
import com.atguigu.scw.user.bean.TMemberAddressExample;
import com.atguigu.scw.user.bean.TMemberExample;
import com.atguigu.scw.user.mapper.TMemberAddressMapper;
import com.atguigu.scw.user.mapper.TMemberMapper;
import com.atguigu.scw.user.service.MemberService;
import com.atguigu.scw.user.vo.request.MemberRequestVo;
import com.netflix.infix.lang.infix.antlr.EventFilterParser.boolean_expr_return;
@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	TMemberMapper memberMapper;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	TMemberAddressMapper memberAddressMapper;
	
	@Override
	public void saveMember(MemberRequestVo vo) {
		//先将vo和member一样属性的值设置给member
		TMember member = new TMember();
		BeanUtils.copyProperties(vo, member);
		member.setUserpswd(passwordEncoder.encode(member.getUserpswd()));
		//设置其他的默认属性值
		member.setUsername(member.getLoginacct());
		member.setAuthstatus("0");//0未认证 1认证中 2认证完成
		memberMapper.insertSelective(member );
	}

	@Override
	public TMember getMemeber(String loginacct, String userpswd) {
		// TODO Auto-generated method stub
		
		
		TMemberExample example = new TMemberExample();
		example.createCriteria().andUsernameEqualTo(loginacct);
		List<TMember> list = memberMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(list)||list.size()>2) {
			return null;
		}
		TMember member = list.get(0);
		//检查密码是否正确
		boolean a = passwordEncoder.matches(userpswd, member.getUserpswd());
		if(!a) {
			return null;
		}
		member.setUserpswd("[PROTECTED]");
		return member;
	}

	@Override
	public List<TMemberAddress> getAllAdress(Integer id) {
		TMemberAddressExample example = new TMemberAddressExample();
		example.createCriteria().andMemberidEqualTo(id);
		return memberAddressMapper.selectByExample(example );
	}

}
