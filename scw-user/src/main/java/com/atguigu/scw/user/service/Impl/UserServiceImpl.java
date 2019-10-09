package com.atguigu.scw.user.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.user.bean.TMember;
import com.atguigu.scw.user.bean.TMemberAddress;
import com.atguigu.scw.user.bean.TMemberAddressExample;
import com.atguigu.scw.user.mapper.TMemberAddressMapper;
import com.atguigu.scw.user.service.MemberService;
import com.atguigu.scw.user.service.UserService;
import com.atguigu.scw.user.vo.request.MemberRequestVo;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	TMemberAddressMapper addressMapper;
	
	@Override
	public List<TMemberAddress> getAddress(Integer memberId) {
		TMemberAddressExample example = new TMemberAddressExample();
		example.createCriteria().andMemberidEqualTo(memberId);
		// TODO Auto-generated method stub
		return addressMapper.selectByExample(example );
	}



}
