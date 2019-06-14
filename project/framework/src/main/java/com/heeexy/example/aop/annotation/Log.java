package com.heeexy.example.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    // 打印的业务类型
    String type() default "";
    
    //日志级别
    LogLevel level() default LogLevel.INFO;

    boolean parameter() default true;
    //打印函数参数的前缀
    String paramPrefix() default "";
    //，
    String paramAppender() default "";
    
    boolean result() default true;
    //打印函数返回值的前缀
    String resultPrefix() default "";
    //，
    String resultAppender() default "";

}