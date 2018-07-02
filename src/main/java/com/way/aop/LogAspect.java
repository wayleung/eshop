package com.way.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
public class LogAspect {

	// 本地异常日志记录对象
	private static final Logger logger = LoggerFactory
			.getLogger(LogAspect.class);

	@Pointcut("execution(* com.way.*..*.*(..))")
	public void logPoincut() {

	}

	@Around("logPoincut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		// 包名 类型名
		String declaringTypeName = joinPoint.getSignature()
				.getDeclaringTypeName();
		// 方法名
		String name = joinPoint.getSignature().getName();
		// Object[] args = joinPoint.getArgs();

		// logger.debug("Around Advice star:"+declaringTypeName+" "+name+" execute");
		logger.debug("环绕通知开始:" + declaringTypeName + " " + name + " 方法执行 ");
		Object ret = joinPoint.proceed(); // 执行目标方法
		logger.debug("环绕通知结束");
		return ret;

	}
}
