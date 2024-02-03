package com.example.platform.aspect.logger;

import com.example.platform.aspect.Constant;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ControllerLogger {
    public static final Logger LOGGER = LoggerFactory.getLogger(ControllerLogger.class);

    @AfterReturning("execution(public * com.example.platform.controller.*.getAll())")
    public void afterFindAllRecords(JoinPoint joinPoint) {
        LOGGER.info(Constant.RECEIVED_ALL + getReturnType(joinPoint));
    }

    @AfterReturning("execution(public * com.example.platform.controller.*.get())")
    public void afterFindByIdRecord(JoinPoint joinPoint) {
        LOGGER.info(Constant.RECEIVED_BY_ID + getReturnType(joinPoint));
    }

    @AfterReturning("execution(public * com.example.platform.controller.*.create())")
    public void afterCreateRecord(JoinPoint joinPoint) {
        LOGGER.info(Constant.CREATE_RECORD + getReturnType(joinPoint));
    }

    @AfterReturning("execution(public * com.example.platform.controller.*.delete())")
    public void afterDeleteByIdRecord(JoinPoint joinPoint) {
        LOGGER.info(Constant.DELETE_BY_ID + getReturnType(joinPoint));
    }

    @AfterReturning("execution(public * com.example.platform.controller.*.update())")
    public void afterUpdateRecord(JoinPoint joinPoint) {
        LOGGER.info(Constant.UPDATE + getReturnType(joinPoint));
    }

    public String getReturnType(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod().getGenericReturnType().getTypeName();
    }

}
