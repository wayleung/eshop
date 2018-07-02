package com.way.action;

import java.beans.IntrospectionException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.way.exception.ErrorCodeConstant;
import com.way.service.ExcelService;
import com.way.vo.Result;


@RequestMapping("file")
@Controller
public class FileAction extends BaseAction {
	
	@Autowired
	ExcelService excelService;
	
	
	/**
	 * 文件上传下载
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam(value="file",required=false)MultipartFile[] files,HttpServletRequest request) throws IllegalStateException, IOException{
		String path = request.getSession().getServletContext().getRealPath("upload");
		for (MultipartFile file : files) {
			String fileName  = file.getOriginalFilename();
			File dir = new File(path,fileName);
			if(!dir.exists()){
				dir.mkdirs();
			}
			//MultipartFile自带的解析方法
			file.transferTo(dir);
		}
		return "ok";
	}
	
	
/*	public String upload(@RequestParam(value="file",required=false)MultipartFile file,HttpServletRequest request) throws IllegalStateException, IOException{
		String path = request.getSession().getServletContext().getRealPath("upload");
		String fileName  = file.getOriginalFilename();
		File dir = new File(path,fileName);
		if(!dir.exists()){
			dir.mkdirs();
		}
		//MultipartFile自带的解析方法
		file.transferTo(dir);
		return "ok";
	}*/
	
	/**
	 * 文件下载功能
	 * @throws IOException 
	 *
	 */
	@RequestMapping(value="/down",method=RequestMethod.GET)
	public void down(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//模拟文件,myfile.txt 为需要下载的文件
		String fileName = request.getSession().getServletContext().getRealPath("upload")+File.separator+"myfile.txt";
		//获取输入流
		InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
		//假如以中文名下载的话
		String filename = "下载文件.txt";
		//转码 免得文件名中文乱码
		filename = URLEncoder.encode(filename, "utf-8");
		//设置文件下载头
		response.addHeader("Content-Disposition", "attachment;filename=" + filename);
		//1.设置文件ContentType类型,这样设置,会自动判断下载文件类型
		response.setContentType("multipart/form-data");
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		int len = 0;
        while((len = bis.read()) != -1){  
            out.write(len);  
            out.flush();  
        }  
        out.close();
	}
	
	
	
	
	/**
	 * 获取下载的文件列表
	 * @param request
	 * @param response
	 * @return 
	 */
	@RequestMapping(value="/getDownloadList",method=RequestMethod.GET)
	//要加入responbody
	@ResponseBody
	public Result getDownloadList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 ServletContext servletContext = request.getSession().getServletContext();
		 String path=servletContext.getRealPath("upload")+File.separator;
		 File[] fileList = new File(path).listFiles();
		 List<String> fileNameList = new ArrayList<String>();
		 for (File file : fileList) {
			 String fileName  = file.getName();
			 fileNameList.add(fileName);
			 System.out.println(fileName);
		}	 
		 return new Result(true, fileNameList, ErrorCodeConstant.E00001.getMessage(), ErrorCodeConstant.E00001.getCode());
	}
	
	
	
	/**
	 * Excel 导入
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/excelImport")
	public String excelImport(@RequestParam(value="excel",required=false)MultipartFile file,HttpServletRequest request) throws Exception {
	     //获取上传的文件

	     InputStream in = file.getInputStream();
	     //数据导入
	     excelService.importExcelInfo(in,file);
	     in.close();
	     return "redirect:/index.html";
	}
	
	/*
	//接着是mapper.xml，用<foreach></foreach>实现数据批量插入
<insert id="insertInfoBatch" parameterType="java.util.List">
    insert into salarymanage (admin_id, serial,company, number, name,sex, card_name, bank_card,
      bank, money, remark,salary_date)
    values
    <foreach collection="list" item="item" index="index" separator=",">
      (#{item.adminId}, #{item.serial}, #{item.company},#{item.number}, #{item.name},
      #{item.sex}, #{item.cardName},#{item.bankCard}, #{item.bank},
      #{item.money}, #{item.remark}, #{item.salaryDate})
    </foreach>
</insert>
	
	*/

		
	/**
	 * excel导出
	 * @param request
	 * @param response
	 * @throws ClassNotFoundException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws ParseException
	 * @throws InvocationTargetException
	 */
	@RequestMapping("/excelExport")
	@ResponseBody
	public void excelExport(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, IntrospectionException, IllegalAccessException, ParseException, InvocationTargetException {
	  
	        response.reset(); //清除buffer缓存
	        Map<String,Object> map=new HashMap<String,Object>();
	        Date date  =  new Date();
	        String date_s  = date.toString();
			// 指定下载的文件名
	        response.setHeader("Content-Disposition", "attachment;filename="+date_s+".xlsx");
	        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	        response.setHeader("Pragma", "no-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setDateHeader("Expires", 0);
	        XSSFWorkbook workbook=null;
	        //导出Excel对象
	        workbook = excelService.exportExcelInfo();
	        OutputStream output;
	        try {
	            output = response.getOutputStream();
	            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
	            bufferedOutPut.flush();
	            workbook.write(bufferedOutPut);
	            bufferedOutPut.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    
	}




	
	
}
