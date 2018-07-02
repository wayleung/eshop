package com.way.dao.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;





import com.way.dao.UserDao;
import com.way.dto.User;
import com.way.vo.Page;

@Repository
public class UserDaoImpl implements UserDao {

	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Override
	public Integer insertUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deleteUserByPrimaryKey(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateUserByPrimaryKey(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> queryAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> queryAllUsersByPage(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User queryUserByPrimaryKey(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer queryUserRecordCounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deleteUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User queryUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	

	

}
