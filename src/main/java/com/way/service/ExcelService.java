package com.way.service;

import java.beans.IntrospectionException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.way.dao.UserDao;
import com.way.dto.User;
import com.way.utils.excel.ExcelBean;
import com.way.utils.excel.ExcelUtil;

@Service
public class ExcelService {
	
	@Autowired
	UserDao userDao;
	
	
	public void importExcelInfo(InputStream in, MultipartFile file) throws Exception{
	    List<List<Object>> listob = ExcelUtil.getBankListByExcel(in,file.getOriginalFilename());
	    List<User> userList = new ArrayList<User>();
	    //遍历listob数据，把数据放到List中
	    for (int i = 0; i < listob.size(); i++) {
	        List<Object> ob = listob.get(i);
	        User user = new User();
	        user.setId(Integer.parseInt(ob.get(0).toString()));
	        user.setUserName(String.valueOf(ob.get(1)));
	        user.setPassword(String.valueOf(ob.get(2)));
	        user.setStatus(String.valueOf(ob.get(3)));
	        user.setIsSuperAdmin(String.valueOf(ob.get(4)));
	        
	        //设置编号
	        //user.setSerial(SerialUtil.salarySerial());
	        //通过遍历实现把每一列封装成一个model中，再把所有的model用List集合装载
/*	        user.setAdminId(adminId);
	        user.setCompany(String.valueOf(ob.get(1)));
	        user.setNumber(String.valueOf(ob.get(2)));
	        user.setName(String.valueOf(ob.get(3)));
	        user.setSex(String.valueOf(ob.get(4)));
	        user.setCardName(String.valueOf(ob.get(5)));
	        user.setBankCard(String.valueOf(ob.get(6)));
	        user.setBank(String.valueOf(ob.get(7)));
	        //object类型转Double类型
	        user.setMoney(Double.parseDouble(ob.get(8).toString()));
	        user.setRemark(String.valueOf(ob.get(9)));
	        user.setSalaryDate(salaryDate);*/
	        
	        
	        //插入
	        //userList.add(user);
	        userDao.insertUser(user);
	        
	        
	    }
	    //批量插入
/*	    for (User user : userList) {
	    	userDao.insertUser(user);
		}*/
	}

	
	
	
	public XSSFWorkbook exportExcelInfo() throws InvocationTargetException, ClassNotFoundException, IntrospectionException, ParseException, IllegalAccessException {
	    //可以根据条件查询数据，把数据装载到一个list中
	    List<User> userList = userDao.queryAllUsers();
/*	    for(int i=0;i<userList.size();i++){
	        int id = userList.get(i).getId();
	        String userName = userList.get(i).getUserName();
	        String password = userList.get(i).getPassword();
	        String status = userList.get(i).getStatus();
	        userList.get(i).setAdminName(adminName);
	        userList.get(i).setId(i+1);
	    }*/
	    List<ExcelBean> excel=new ArrayList<>();
	    Map<Integer,List<ExcelBean>> map=new LinkedHashMap<>();
	    XSSFWorkbook xssfWorkbook=null;
	    //设置标题栏
	    excel.add(new ExcelBean("序号","id",0));
	    excel.add(new ExcelBean("用户名","userName",0));
	    excel.add(new ExcelBean("密码","password",0));
	    excel.add(new ExcelBean("状态","status",0));
	    excel.add(new ExcelBean("是否超管","isSuperAdmin",0));

	    map.put(0, excel);
	    String sheetName = "用户表";
	    //调用ExcelUtil的方法
	    xssfWorkbook = ExcelUtil.createExcelFile(User.class, userList, map, sheetName);
	    return xssfWorkbook;
	}

}
