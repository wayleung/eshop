package com.way.action;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.way.dto.User;
import com.way.exception.ErrorCodeConstant;
import com.way.service.UserService;
import com.way.service.impl.UserServiceImpl;
import com.way.utils.redis.cache.RedisCache;
import com.way.vo.Page;
import com.way.vo.PageResult;
import com.way.vo.Result;

@RequestMapping("user")
@Controller
public class UserAction extends BaseAction {

	@Autowired
	UserService userService;

	private static final Logger logger = LoggerFactory
			.getLogger(UserAction.class);

	@ResponseBody
	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	Result login(String userName, String password) {

		// shiro subject主体
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userName,
				password);

		try {
			subject.login(token);

		} catch (UnknownAccountException uae) {
			// 用户名未知...
			return Result.falseResult("用户不存在",
					ErrorCodeConstant.E00000.getCode());
		} catch (IncorrectCredentialsException ice) {
			// 凭据不正确，例如密码不正确 ...
			return Result.falseResult("密码不正确",
					ErrorCodeConstant.E00000.getCode());
		} catch (LockedAccountException lae) {
			// 用户被锁定，例如管理员把某个用户禁用...
			return Result.falseResult("用户被禁用",
					ErrorCodeConstant.E00000.getCode());
		} catch (ExcessiveAttemptsException eae) {
			// 尝试认证次数多余系统指定次数 ...
			return Result.falseResult("请求次数过多，用户被锁定",
					ErrorCodeConstant.E00000.getCode());
		}

		catch (AuthenticationException e) {
			return Result.falseResult(e.getMessage(),
					ErrorCodeConstant.E00000.getCode());
		}

		if(subject.hasRole("admin"))
		{ 
			return Result.trueResult(ErrorCodeConstant.E00001.getMessage()+"有admin权限",
					ErrorCodeConstant.E00001.getCode());
			} 
		
		return Result.trueResult(ErrorCodeConstant.E00001.getMessage()+"无admin权限",
				ErrorCodeConstant.E00001.getCode());
		
		//return Result.trueResult(ErrorCodeConstant.E00001.getMessage(),ErrorCodeConstant.E00001.getCode());

		
		 
		 

		/*
		 * boolean flag = userService.login(account, password); if(flag){ return
		 * Result.trueResult(ErrorCodeConstant.E00001.getMessage(),
		 * ErrorCodeConstant.E00001.getCode()); } return
		 * Result.falseResult(ErrorCodeConstant.E00000.getMessage(),
		 * ErrorCodeConstant.E00000.getCode());
		 */
	}

	@ResponseBody
	@RequestMapping(value = "/deleteUserByUserName", method = RequestMethod.POST)
	Result deleteUserByUserName(String userName) {
		Integer deleteUserByUserName = userService.deleteUserByUserName(userName);
		if (deleteUserByUserName != 0) {
			return Result.trueResult(ErrorCodeConstant.E00001.getMessage()+", 改动的行数"+deleteUserByUserName,
					ErrorCodeConstant.E00001.getCode());
		} else {
			return Result.falseResult(ErrorCodeConstant.E00000.getMessage(),
					ErrorCodeConstant.E00000.getCode());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/insertUser", method = RequestMethod.POST)
	Result insertUser(User user) {
		Integer insert = userService.insertUser(user);
		/*if (insert != 0) {*/
		//Mybatis设置了返回自增主键
		if (insert != null&&insert >= 0) {
			return Result.trueResult(ErrorCodeConstant.E00001.getMessage()+", 新增的id为:"+insert,
					ErrorCodeConstant.E00001.getCode());
		} else {
			return Result.falseResult(ErrorCodeConstant.E00000.getMessage(),
					ErrorCodeConstant.E00000.getCode());
		}

	}

	@ResponseBody
	@RequestMapping(value = "/deleteUserByPrimaryKey", method = RequestMethod.POST)
	Result deleteUserByPrimaryKey(Integer id) {
		Integer deletByPrimaryKey = userService.deleteUserByPrimaryKey(id);
		if (deletByPrimaryKey != 0) {
			return Result.trueResult(ErrorCodeConstant.E00001.getMessage()+", 改动的行数"+deletByPrimaryKey,
					ErrorCodeConstant.E00001.getCode());
		} else {
			return Result.falseResult(ErrorCodeConstant.E00000.getMessage(),
					ErrorCodeConstant.E00000.getCode());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updateUserByPrimaryKey", method = RequestMethod.POST)
	Result updateUserByPrimaryKey(User user) {
		Integer updateByPrimaryKey = userService.updateUserByPrimaryKey(user);
		if (updateByPrimaryKey != 0) {
			return Result.trueResult(ErrorCodeConstant.E00001.getMessage()+", 改动的行数"+updateByPrimaryKey,
					ErrorCodeConstant.E00001.getCode());
		} else {
			return Result.falseResult(ErrorCodeConstant.E00000.getMessage(),
					ErrorCodeConstant.E00000.getCode());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryAllUsers", method = RequestMethod.GET)
	Result queryAllUsers() {
		List<User> queryAll = userService.queryAllUsers();
		if (queryAll != null && queryAll.size() > 0) {
			return new Result(true, queryAll,
					ErrorCodeConstant.E00001.getMessage(),
					ErrorCodeConstant.E00001.getCode());

		} else {
			return Result.falseResult(ErrorCodeConstant.E00000.getMessage(),
					ErrorCodeConstant.E00000.getCode());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryAllUsersByPage", method = RequestMethod.POST)
	Result queryAllUsersByPage(Page page) {
		PageResult<User> queryAllByPage = userService.queryAllUsersByPage(page);
		if (queryAllByPage != null && queryAllByPage.getData() != null
				&& queryAllByPage.getData().size() > 0) {
			return new Result(true, queryAllByPage,
					ErrorCodeConstant.E00001.getMessage(),
					ErrorCodeConstant.E00001.getCode());

		} else {
			return Result.falseResult(ErrorCodeConstant.E00000.getMessage(),
					ErrorCodeConstant.E00000.getCode());
		}

	}

	@ResponseBody
	@RequestMapping(value = "/queryUserByPrimaryKey", method = RequestMethod.GET)
	Result queryUserByPrimaryKey(Integer id) {
		User queryByPrimaryKey = userService.queryUserByPrimaryKey(id);
		if (queryByPrimaryKey != null) {
			return new Result(true, queryByPrimaryKey,
					ErrorCodeConstant.E00001.getMessage(),
					ErrorCodeConstant.E00001.getCode());

		} else {
			return Result.falseResult(ErrorCodeConstant.E00000.getMessage(),
					ErrorCodeConstant.E00000.getCode());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryUserByUserName", method = RequestMethod.GET)
	Result queryUserByUserName(String userName) {
		User queryByUserName = userService.queryUserByUserName(userName);
		if (queryByUserName != null) {
			return new Result(true, queryByUserName,
					ErrorCodeConstant.E00001.getMessage(),
					ErrorCodeConstant.E00001.getCode());

		} else {
			return Result.falseResult(ErrorCodeConstant.E00000.getMessage(),
					ErrorCodeConstant.E00000.getCode());
		}
	}
	

	@ResponseBody
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	Result users() {
		List<User> queryAll = userService.queryAllUsers();
		if (queryAll != null && queryAll.size() > 0) {
			return new Result(true, queryAll,
					ErrorCodeConstant.E00001.getMessage(),
					ErrorCodeConstant.E00001.getCode());

		} else {
			return Result.falseResult(ErrorCodeConstant.E00000.getMessage(),
					ErrorCodeConstant.E00000.getCode());
		}
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	Result users_insert(User user) {
		Integer insert = userService.insertUser(user);
		/*if (insert != 0) {*/
		//Mybatis设置了返回自增主键
		if (insert!=null&&insert >= 0) {
			return Result.trueResult(ErrorCodeConstant.E00001.getMessage()+", 新增的id为:"+insert,
					ErrorCodeConstant.E00001.getCode());
		} else {
			return Result.falseResult(ErrorCodeConstant.E00000.getMessage(),
					ErrorCodeConstant.E00000.getCode());
		}

	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	Result users_id(@PathVariable("id") Integer id) {
		User queryByPrimaryKey = userService.queryUserByPrimaryKey(id);
		if (queryByPrimaryKey != null) {
			return new Result(true, queryByPrimaryKey,
					ErrorCodeConstant.E00001.getMessage(),
					ErrorCodeConstant.E00001.getCode());

		} else {
			return Result.falseResult(ErrorCodeConstant.E00000.getMessage(),
					ErrorCodeConstant.E00000.getCode());
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	Result users_deleteid(@PathVariable("id") Integer id) {
		Integer deletByPrimaryKey = userService.deleteUserByPrimaryKey(id);
		if (deletByPrimaryKey != 0) {
			return Result.trueResult(ErrorCodeConstant.E00001.getMessage()+", 改动的行数"+deletByPrimaryKey,
					ErrorCodeConstant.E00001.getCode());
		} else {
			return Result.falseResult(ErrorCodeConstant.E00000.getMessage(),
					ErrorCodeConstant.E00000.getCode());
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/users/", method = RequestMethod.PUT)
	Result users_updateid(User user) {
		Integer updateByPrimaryKey = userService.updateUserByPrimaryKey(user);
		if (updateByPrimaryKey != 0) {
			return Result.trueResult(ErrorCodeConstant.E00001.getMessage()+", 改动的行数"+updateByPrimaryKey,
					ErrorCodeConstant.E00001.getCode());
		} else {
			return Result.falseResult(ErrorCodeConstant.E00000.getMessage(),
					ErrorCodeConstant.E00000.getCode());
		}
	}
	

	@ResponseBody
	@RequestMapping(value = "/testTransaction", method = RequestMethod.POST)
	Result testTransaction() {
/*		User user1 = new User();
		user1.setUserName("user1");
		user1.setPassword("user1");
		user1.setStatus("A");
		user1.setIsSuperAdmin("Y");
		Integer insert1 = userService.insertUser(user1);
		
		User user2 = new User();
		int error = 9/0;
		user2.setUserName("user2");
		user2.setPassword("user2");
		user2.setStatus("A");
		user2.setIsSuperAdmin("Y");
		Integer insert2 = userService.insertUser(user1);
		*/
		/*if (insert != 0) {*/
		//Mybatis设置了返回自增主键
/*		if (insert1 != null&&insert2 != null&&insert1 >= 0&&insert2 >= 0) {
			return Result.trueResult(ErrorCodeConstant.E00001.getMessage()+", 新增的id1为:"+insert1+", 新增的id2为:"+insert2,
					ErrorCodeConstant.E00001.getCode());
		} else {
			return Result.falseResult(ErrorCodeConstant.E00000.getMessage(),
					ErrorCodeConstant.E00000.getCode());
		}
*/
		
		userService.testTransaction();
		return Result.trueResult("testTransaction", "1");
	}

}
