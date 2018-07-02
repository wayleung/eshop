package com.way.utils.redis.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisUtils;

import com.way.utils.redis.JedisUtil;
import com.way.utils.redis.RedisUtil;





public class MethodCacheInterceptor implements MethodInterceptor {  
  
    private static final Logger logger = LoggerFactory.getLogger(MethodCacheInterceptor.class);  
  
    private RedisUtil redisUtil;  
    private List<String> targetNamesList; // 不加入缓存的service名称  
    private List<String> methodNamesList; // 不加入缓存的方法名称  
    private Long defaultCacheExpireTime; // 缓存默认的过期时间  
  
    /** 
     * 初始化读取不需要加入缓存的类名和方法名称 
     */  
    public MethodCacheInterceptor() {  
        try {  
            if (logger.isInfoEnabled()) {  
                logger.info("进入redis缓存切面类,MethodCacheInterceptor");  
                logger.info("读取redis配置文件redis.properties,其绝对路劲为:"  
                        + MethodCacheInterceptor.class.getResource("/redis.properties").getFile());  
            }  
            File f = new File(MethodCacheInterceptor.class.getResource("/redis.properties").getFile());  
            // 配置文件位置直接被写死，有需要自己修改下  
            InputStream in = new FileInputStream(f);  
            Properties p = new Properties();  
            p.load(in);  
            // 分割字符串  
            String[] targetNames = p.getProperty("targetNames").split(",");  
            String[] methodNames = p.getProperty("methodNames").split(",");  
  
            // 加载过期时间设置  
            defaultCacheExpireTime = Long.valueOf(p.getProperty("defaultCacheExpireTime"));  
            // 创建list  
            targetNamesList = new ArrayList<String>(targetNames.length);  
            methodNamesList = new ArrayList<String>(methodNames.length);  
            Integer maxLen = targetNames.length > methodNames.length ? targetNames.length : methodNames.length;  
            // 将不需要缓存的类名和方法名添加到list中  
            for (int i = 0; i < maxLen; i++) {  
                if (i < targetNames.length) {  
                    targetNamesList.add(targetNames[i]);  
                }  
                if (i < methodNames.length) {  
                    methodNamesList.add(methodNames[i]);  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    @Override  
    public Object invoke(MethodInvocation invocation) throws Throwable {  
          
        if (logger.isInfoEnabled()) {  
            logger.info("进入redis缓存切面类的invoke方法,MethodCacheInterceptor.invoke(invocation)");  
        }  
          
        Object value = null;  
  
        String targetName = invocation.getThis().getClass().getName();  
        String methodName = invocation.getMethod().getName();  
          
        Boolean isNeedCache=isAddCache(targetName, methodName); 
        Object[] arguments = invocation.getArguments();  
        String key = getCacheKey(targetName, methodName, arguments); 
        
        
        /*
         * 自己修改:若是增删改方法则刷新缓存 并调用方法 返回值
         * redis 缓存处理 假如更新 删除 新增了 则清楚缓存 
         */
        if(key.contains("_insert")||key.contains("_delete")||key.contains("_create")||key.contains("_update")){
        	value = invocation.proceed();
        	 if (logger.isInfoEnabled()) {  
                 logger.info("增删改方法则刷新缓存 value:"+value);  
             }  
        	 redisUtil.removePattern("*");
        	 
        	return value;
        }
        
        
        if (logger.isInfoEnabled()) {  
            logger.info("判断当前类和方法是否不需要缓存(配置文件中设定有默认的非缓存类和方法),是否需要缓存:"+isNeedCache);  
        }  
        if (!isNeedCache) {  
            // 若不需要缓存则执行方法返回结果  
            return invocation.proceed();  
        }  
        
        
        
       
        
        
        if (logger.isInfoEnabled()) {  
            logger.info("获取缓存key="+key);  
        }  
        try {  
            if (logger.isInfoEnabled()) {  
                logger.info("判断是否存在缓存,isExist:"+redisUtil.exists(key));  
            }  
            // 判断是否有缓存  
            if (redisUtil.exists(key)) {  
                if (logger.isInfoEnabled()) {  
                    logger.info("若该key对应的缓存已存在,则直接返回缓存值,value="+redisUtil.get(key));  
                }  
                return redisUtil.get(key);  
            }  
            // 写入缓存  
            value = invocation.proceed();  
            if (logger.isInfoEnabled()) {  
                logger.info("若该key对应的缓存不存在,则反射获取当前方法返回结果作为该key的缓存值value="+value);  
            }  
            if (value != null) {  
                final String tkey = key;  
                final Object tvalue = value;  
                new Thread(new Runnable() {  
                    @Override  
                    public void run() {  
                        if (logger.isInfoEnabled()) {  
                            logger.info("写入当前key对应的value,{key:"+tkey+",value:"+tvalue+"}");  
                        }  
                        redisUtil.set(tkey, tvalue, defaultCacheExpireTime);  
  
                    }  
                }).start();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            if (value == null) {  
                return invocation.proceed();  
            }  
        }  
        return value;  
    }  
  
    /** 
     * 是否加入缓存 
     *  
     * @return 
     */  
    private boolean isAddCache(String targetName, String methodName) {  
        boolean flag = true;  
        if (targetNamesList.contains(targetName) || methodNamesList.contains(methodName)) {  
            flag = false;  
        }  
        return flag;  
    }  
  
    /** 
     * 创建缓存key 
     *  
     * @param targetName 
     * @param methodName 
     * @param arguments 
     */  
    private String getCacheKey(String targetName, String methodName, Object[] arguments) {  
        StringBuffer sbu = new StringBuffer();  
        sbu.append(targetName).append("_").append(methodName);  
        if ((arguments != null) && (arguments.length != 0)) {  
            for (int i = 0; i < arguments.length; i++) {  
                sbu.append("_").append(arguments[i]);  
            }  
        }  
        return sbu.toString();  
    }  
  
    public void setRedisUtil(RedisUtil redisUtil) {  
        this.redisUtil = redisUtil;  
    }  
}  