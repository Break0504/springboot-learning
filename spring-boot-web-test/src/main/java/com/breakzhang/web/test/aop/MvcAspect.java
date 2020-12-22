package com.breakzhang.web.test.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 17:23 2020/12/14
 * @description:
 */
@Aspect
@Component
public class MvcAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(MvcAspect.class);

    public static final String EDP = "execution(* com.breakzhang.web.test.controller.*Controller.*(..))";
    public static final String PC_NM = "point()";

    public MvcAspect() { }

    /**
     * 定义切入点
     */
    @Pointcut(EDP)
    public void point() { }

    /**
     * 在切入点开始处切入内容
     * @param joinPoint
     * @throws Throwable
     */
    @Before(PC_NM)
    public void before(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        if (joinPoint.getArgs().length < 1 || joinPoint.getArgs()[0] == null) {
            return;
        }

        LOGGER.debug("--request IP:{}, {}.{}", request.getRemoteAddr(), joinPoint.getTarget().getClass().getName(),
                ((MethodSignature) joinPoint.getSignature()).getMethod().getName());
        LOGGER.debug("--request param：{}", JSON.toJSONString(joinPoint.getArgs()));
    }

    @Around(PC_NM)
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object obj = null;
        try {
            obj = joinPoint.proceed();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return obj;
        } finally {
            LOGGER.debug("耗时 : {}ms, --MvcAspect Response: {}", (System.currentTimeMillis() - startTime), JSON.toJSONString(joinPoint.getArgs()));
        }

    }







}
