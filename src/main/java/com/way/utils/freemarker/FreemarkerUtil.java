package com.way.utils.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;



/**
 * 
 * @author Way Liang ASNPHXW
 *
 * @date Jul 3, 2018 
 *
 * @description:
 *
 */

@Component
public class FreemarkerUtil {
	
	static FreeMarkerConfigurer freeMarkerConfigurer;
	
    @Autowired(required = true)
    //Spring不支持依赖注入static静态变量  setter 注入  不能为static方法
	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		FreemarkerUtil.freeMarkerConfigurer = freeMarkerConfigurer;
	}


	public static void main(String[] args) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException{
	
		// 添加数据
/*		Map<String, Object> datamap = new HashMap<String, Object>();
		datamap.put("summary", new Summary("2018-07-03 17:51:00", "2018-07-03 17:52:00", 2, 6, 6, 6,6, 0.6, 0.7));
		List<Case> lists = new ArrayList<Case>();
		lists.add(new Case("baidu", "SUCCESS", 20, 0, 10));
		lists.add(new Case("sina", "Fail", 20, 0, 10));
		datamap.put("cases", lists);
		ftlToHtml(datamap, "summary.ftl", "src"+File.separator+"main"+File.separator+"webapp"+File.separator+"WEB-INF"+File.separator+"html"+File.separator+"summary.html");
		
		
		
		Map<String, Object> datamap2 = new HashMap<String, Object>();
		List<Detail> lists2 = new ArrayList<Detail>();
		lists2.add(new Detail("description", "SUCCESS", "message"));
		lists2.add(new Detail("description2", "SUCCESS", "message2"));
		datamap2.put("case", new Case("baidu", "SUCCESS", 20, 0, 10));
		datamap2.put("details", lists2);
		ftlToHtml(datamap2, "case.ftl", "src"+File.separator+"main"+File.separator+"webapp"+File.separator+"WEB-INF"+File.separator+"html"+File.separator+"TestCase"+File.separator+"baidu.html");
		
		
		Map<String, Object> datamap3 = new HashMap<String, Object>();
		List<Detail> lists3 = new ArrayList<Detail>();
		lists3.add(new Detail("description", "SUCCESS", "message"));
		lists3.add(new Detail("description2", "SUCCESS", "message2"));
		datamap3.put("case", new Case("sina", "Fail", 20, 0, 10));
		datamap3.put("details", lists3);
		ftlToHtml(datamap3, "case.ftl", "src"+File.separator+"main"+File.separator+"webapp"+File.separator+"WEB-INF"+File.separator+"html"+File.separator+"TestCase"+File.separator+"sina.html");
		
		*/
		
		Summary summary = new Summary("2018-07-04 13:42:00", "2018-07-04 13:42:00", "2", "6", "6", "6","6", "0.6", "0.7");
		
		Case google = new Case("google", "SUCCESS", "20s", "0", "10");
		Case yahoo = new Case("yahoo", "SUCCESS", "20s", "0", "10");
		Case sina = new Case("sina", "Fail", "20s", "0", "10");
		Case alibaba = new Case("alibaba", "Fail", "20s", "0", "10");	
		List<Case> cases = new ArrayList<Case>();
		cases.add(google);
		cases.add(yahoo);
		cases.add(sina);
		cases.add(alibaba);
		
		summary.setCases(cases);
		
		
		List<Detail> details = new ArrayList<Detail>();
		details.add(new Detail("1","comment","description", "message", "SUCCESS"));
		details.add(new Detail("2","comment2","description2", "message2", "SUCCESS"));
		
		google.setDetails(details);
		yahoo.setDetails(details);
		sina.setDetails(details);
		alibaba.setDetails(details);
		
/*		generateCaseHtml(null,google);
		generateCaseHtml(null,yahoo);
		generateCaseHtml(null,sina);
		generateCaseHtml(null,alibaba);
		
		generateSummaryHtml(null,summary);*/
		
		generateSummaryAndCaseHtml(null,summary);
	}
	
	
	public  static String welcome(String name) {
		return "您好:"+name+",欢迎访问demo！";
	}
	 
	
	/**
	 * ftl模板转html 注意模板的key  必须跟 map的key对应
	 * @param datamap
	 * @param ftlName	包括后缀名 如 summary.ftl
	 * @param outPutPath   包括后缀名 如 D:\\Project\\Automation\\src\\main\\webapp\\WEB-INF\\html\\summary.html
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws MalformedTemplateNameException 
	 * @throws TemplateNotFoundException 
	 * @throws TemplateException 
	 */
	public static void ftlToHtml(Map datamap,String ftlName,String outPutPath) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException{

		//获取ftl模板
		Template template = getTemplateByFtlName(ftlName);
		
		String substring = outPutPath.substring(0, outPutPath.lastIndexOf(File.separator));  
		//System.out.println(substring);
		File f = new File(substring);
		
		if(!f.exists()){
			f.mkdirs();
		}
		
		// 输出文件路径
		Writer wr = new FileWriter(outPutPath);
		// 写入
		template.process(datamap, wr);
		// 关闭流
		wr.close();
	}
	
	
	/**
	 * 生成case result html页面到指定路径
	 * @param folderName 存放html页面的文件夹名 假如为空的话则命名为时间戳
	 * @param kase
	 * @throws TemplateNotFoundException
	 * @throws MalformedTemplateNameException
	 * @throws ParseException
	 * @throws IOException
	 * @throws TemplateException
	 */
	//case是java关键字 不能做变量名
	public static void generateCaseHtml(String folderName,Case kase) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException{
		if(StringUtils.isBlank(folderName)){
			folderName = "html_"+String.valueOf(new Date().getTime());
		}
		
		
		List<Detail> details = kase.getDetails();
		
		//获取ftl模板
		Template template = getTemplateByFtlName("case.ftl");
		
		//String outPutPath = "src"+File.separator+"main"+File.separator+"webapp"+File.separator+"WEB-INF"+File.separator+"html"+File.separator+"TestCase"+File.separator+kase.getCaseName()+".html";
		String outPutPath = folderName+File.separator+"TestCase"+File.separator+kase.getCaseName()+".html";
		String substring = outPutPath .substring(0, outPutPath.lastIndexOf(File.separator));  
		//System.out.println(substring);
		File f = new File(substring);
		
		Map<String, Object> datamap = new HashMap<String, Object>();
		datamap.put("case", kase);
		datamap.put("details", details);
		//System.out.println("---generateCaseHtml "+outPutPath);
		
		if(!f.exists()){
			f.mkdirs();
		}
		
		// 输出文件路径
		Writer wr = new FileWriter(outPutPath);

		// 写入
		template.process(datamap, wr);
		// 关闭流
		wr.close();
		
	}
	
	
	/**
	 * 生成summary html页面到指定路径
	 * @param folderName 存放html页面的文件夹名 假如为空的话则命名为时间戳
	 * @param summary
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void generateSummaryHtml(String folderName,Summary summary) throws IOException, TemplateException{
		if(StringUtils.isBlank(folderName)){
			folderName = "html_"+String.valueOf(new Date().getTime());
		}
		
		List<Case> cases  = summary.getCases();
		
		//获取ftl模板
		Template template = getTemplateByFtlName("summary.ftl");
		
		//String outPutPath = "src"+File.separator+"main"+File.separator+"webapp"+File.separator+"WEB-INF"+File.separator+"html"+File.separator+"summary.html";
		String outPutPath = folderName+File.separator+"summary.html";
		String substring = outPutPath .substring(0, outPutPath.lastIndexOf(File.separator));  
		//System.out.println(substring);
		File f = new File(substring);
		
		Map<String, Object> datamap = new HashMap<String, Object>();
		datamap.put("summary", summary);
		datamap.put("cases", cases);
		//System.out.println("---generateSummaryHtml "+outPutPath);
		
		if(!f.exists()){
			f.mkdirs();
		}
		
		// 输出文件路径
		Writer wr = new FileWriter(outPutPath);

		// 写入
		template.process(datamap, wr);
		// 关闭流
		wr.close();
	}
	
	

	/**
	 * 直接生成summary和case的html页面
	 * @param folderName 存放html页面的文件夹名 假如为空的话则命名为时间戳
	 * @param summary
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void generateSummaryAndCaseHtml(String folderName,Summary summary) throws IOException, TemplateException{
		if(StringUtils.isBlank(folderName)){
			folderName = "html_"+String.valueOf(new Date().getTime());
		}
		
		generateSummaryHtml(folderName,summary);
		
		List<Case> cases  = summary.getCases();
		
		if(cases != null && cases.size()>=0){
			for (Case kase : cases) {
				generateCaseHtml(folderName,kase);
			}
		}
		
	
	}
	
	
	
	
	
	/**
	 * 根据模板名获取ftl模板
	 * @param ftlName
	 * @return
	 * @throws TemplateNotFoundException
	 * @throws MalformedTemplateNameException
	 * @throws ParseException
	 * @throws IOException
	 */
	public static Template getTemplateByFtlName(String ftlName) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException{
/*		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:/applicationContext.xml");*/
		
		
		//ApplicationContext context = new ClassPathXmlApplicationContext(BeanFactory.class.getResource("/")+"/applicationContext.xml");
		
		/*在jar包里ClassPathXmlApplicationContext不能读取?
		 * <property name="templateLoaderPath" value="d:/freemarker/" />
		 * */
		//不用spring配置
		
        // 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
        //Configuration configuration = new Configuration(Configuration.getVersion());
        // 第二步：设置模板文件所在的路径。
        //configuration.setDirectoryForTemplateLoading(new File("."+File.separator+"freemarker/"));
        // 第三步：设置模板文件使用的字符集。一般就是utf-8.
        //configuration.setDefaultEncoding("utf-8");
        
        
        
        
        //spring 配置
		//ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
		//FreeMarkerConfigurer freeMarkerConfigurer = (FreeMarkerConfigurer) context.getBean("freeMarkerConfigurer");
		// 获取连接
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
        
		
		// 获取ftl,由于已经在配置文件中配置了路径所以在这里直接写模板名称就可以
		Template template = configuration.getTemplate(ftlName);
		return template;
	}

}
