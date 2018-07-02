package com.way.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.way.dao.UserDao;
import com.way.dao.impl.UserDaoImpl;
import com.way.dto.User;
import com.way.service.UserService;
import com.way.utils.redis.cache.RedisCache;
import com.way.vo.Page;
import com.way.vo.PageResult;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@RedisCache
	@Override
	public boolean login(String account, String password) {
		logger.debug("userservice login");
		logger.debug("account:" + account + " password:" + password);
		List<User> list = userDao.queryAllUsers();
		for (User user : list) {
			if (user.getUserName().equals(account)
					&& user.getUserName().equals(password)) {
				return true;
			}
			;
		}
		// TODO Auto-generated method stub
		return false;
	}

	@RedisCache
	@Override
	public Integer deleteUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return userDao.deleteUserByUserName(userName);
	}


	@RedisCache
	@Override
	public Integer insertUser(User user) {
		// TODO Auto-generated method stub
		userDao.insertUser(user);
		return user.getId();
	}

	@RedisCache
	@Override
	public Integer deleteUserByPrimaryKey(Object key) {
		// TODO Auto-generated method stub
		return userDao.deleteUserByPrimaryKey(key);
	}

	@RedisCache
	@Override
	public Integer updateUserByPrimaryKey(User t) {
		// TODO Auto-generated method stub
		return userDao.updateUserByPrimaryKey(t);
	}
	
	@RedisCache
	@Override
	public List<User> queryAllUsers() {
		// TODO Auto-generated method stub
		return userDao.queryAllUsers();
	}

	@RedisCache
	@Override
	public PageResult<User> queryAllUsersByPage(Page page) {
		// TODO Auto-generated method stub

		Integer startPos = page.getStartPos();
		Integer pageSize = page.getPageSize();
		Integer currentPage = page.getPageNow();
		Integer recordCount  = queryUserRecordCounts();
		page.setTotalCount(recordCount);
		Integer totalPage = page.getTotalPageCount();
		if(currentPage<1){
			page.setPageNow(1);
			currentPage = 1;
			
		}else if(currentPage>page.getTotalPageCount()){
			page.setPageNow(totalPage);
			currentPage = totalPage;
		}
		List<User> queryAllByPage = userDao.queryAllUsersByPage(page);
		return new PageResult<User>(queryAllByPage, queryUserRecordCounts(),
				pageSize, currentPage);
	}

	@RedisCache
	@Override
	public User queryUserByPrimaryKey(Object key) {
		// TODO Auto-generated method stub
		return userDao.queryUserByPrimaryKey(key);
	}

	@RedisCache
	@Override
	public Integer queryUserRecordCounts() {
		// TODO Auto-generated method stub
		return userDao.queryUserRecordCounts();
	}

	@RedisCache
	@Override
	public User queryUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return userDao.queryUserByUserName(userName);
	}
	
	
	/**
	 * 事务测试
	 */
	@RedisCache
	@Transactional
	@Override
	public void testTransaction() {
		// TODO Auto-generated method stub
		
		User user1 = new User();
		user1.setUserName("user1");
		user1.setPassword("user1");
		user1.setStatus("A");
		user1.setIsSuperAdmin("Y");
		Integer insert1 = userDao.insertUser(user1);
		
		User user2 = new User();
		int error = 9/0;
		user2.setUserName("user2");
		user2.setPassword("user2");
		user2.setStatus("A");
		user2.setIsSuperAdmin("Y");
		Integer insert2 = userDao.insertUser(user1);
		
		
		
	}
}
