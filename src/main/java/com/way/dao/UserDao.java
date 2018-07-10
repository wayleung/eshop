package com.way.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.way.dto.User;
import com.way.vo.Page;

/*@CacheConfig(cacheNames = "post") */
public interface UserDao {

	/*@CachePut(cacheNames = "post",key = "#p0.id")*/  
	Integer insertUser(User user);
	
	/*@CacheEvict(cacheNames = "post",key = "#p0")*/
	Integer deleteUserByPrimaryKey(Object key);
	
	Integer updateUserByPrimaryKey(User user);
	
	List<User> queryAllUsers();
	
	List<User> queryAllUsersByPage(Page page);
	
	/*@Cacheable(cacheNames = "post",key = "#p0")*/
	User queryUserByPrimaryKey(Object key);
	
	Integer queryUserRecordCounts();

	
	Integer deleteUserByUserName(String userName);

	User queryUserByUserName(String userName);
}
