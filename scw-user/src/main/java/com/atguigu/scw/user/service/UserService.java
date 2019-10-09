package com.atguigu.scw.user.service;

import java.util.List;

import com.atguigu.scw.user.bean.TMemberAddress;

public interface UserService {

	List<TMemberAddress> getAddress(Integer memberId);

}
