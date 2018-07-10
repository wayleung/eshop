package com.way.utils.http;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HttpClientTest {  
    public static void main(String[] args) {  
        ApplicationContext context = new ClassPathXmlApplicationContext(  
                "classpath:spring/spring-*.xml");  
        System.out.println(context);  
  
        HttpService httpService = (HttpService) context.getBean("httpService");  
        System.out.println(httpService);  
        try {  

             String string = httpService.doGet("http://www.baidu.com/s");  
             System.out.println(string);  
/*  
            Map<String, Object> maps = new HashMap<String, Object>();  
            maps.put("wd", "java");  
            String string = httpService.doPost(  
                    "http://localhost:8080/ssss/HaHaServlet", maps);  
            System.out.println(string);  
  */
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    }  
} 