package com.way.service;

import java.util.List;

import com.way.dto.User;
import com.way.vo.Page;
import com.way.vo.PageResult;

public interface UserService {

	boolean login(String account, String password);

	Integer deleteUserByUserName(String userName);

	Integer insertUser(User user);

	Integer deleteUserByPrimaryKey(Object key);

	Integer updateUserByPrimaryKey(User user);

	List<User> queryAllUsers();

	// public List<User> queryAllByPage(Integer startPos, Integer pageSize);
	PageResult<User> queryAllUsersByPage(Page page);

	User queryUserByPrimaryKey(Object key);

	Integer queryUserRecordCounts();

	User queryUserByUserName(String userName);
	
	void testTransaction();

}
