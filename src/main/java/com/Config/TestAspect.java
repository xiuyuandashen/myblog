package com.Config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
//@Aspect
public class TestAspect {
    //@Pointcut("execution(* com.dao..*.*(..))")
    public void test1(){}

    //@Before("test1()")
    public void advice1(){
        System.out.println("before...");
    }
}
