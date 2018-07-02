package com.way.utils.redis.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * 自定义注解RedisCache  redis缓存  加上该注解后方法做缓存
 * @author HASEE 
 * 
 */  
@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME)  
public @interface RedisCache {  
      
    /** 
     * key 
     * @return 
     */  
    String key() default "";  
      
    /** 
     * value 
     * @return 
     */  
    String value() default "";  
      
}  