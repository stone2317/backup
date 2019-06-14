package com.heeexy.example.aop.aspect;

import java.lang.reflect.Method;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.heeexy.example.aop.annotation.Log;
import com.heeexy.example.aop.annotation.LogLevel;
import com.heeexy.example.util.StringTools;




@Aspect
@Component
public class LogAspect{
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("@annotation(com.heeexy.example.aop.annotation.Log)")
    public void pointcut() {
    }
    
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //ImmutableList keys = ImmutableList.of("");
    	String keys = "satlink,scaResource";
        LogLevel logLevel = LogLevel.INFO;
   	
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method signatureMethod = signature.getMethod();
        Log logAnno = signatureMethod.getAnnotation(Log.class);
        
        if(logAnno.level().compareTo(logLevel) >=0 &&
        		keys.contains(logAnno.type()) &&
        		logAnno.parameter()){
        	logger.info(signatureMethod.getName() +logAnno.paramPrefix()+ StringTools.array2String(joinPoint.getArgs())+logAnno.paramAppender());
        }
        Object ret = joinPoint.proceed();
        if(signatureMethod.getReturnType().equals(Void.TYPE)){
        	return ret;
        }
        if (logAnno.result()) {
			logger.info(signatureMethod.getName() + ret.toString());
		}
        return ret;
    }

}