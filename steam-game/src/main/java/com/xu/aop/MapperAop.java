package com.xu.aop;


import com.alibaba.druid.util.StringUtils;
import com.xu.bean.DtoBase;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Aspect
@Configuration
public class MapperAop {

    @Pointcut("execution(* com.xu.mapper..*.*(..))")
    public void mapperPointer(){

    }

    @Before("mapperPointer()")
    public void before(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        if(args.length==1&&args[0] instanceof DtoBase){
            setDtoBaseInfo((DtoBase) args[0],methodName);
        }
    }

    private void setDtoBaseInfo(DtoBase dtoBase,String methodName){
        if(StringUtils.isEmpty(methodName))return;
        if(methodName.startsWith("insert")){
            dtoBase.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
            dtoBase.setCreateUser(1L);
        }else if(methodName.startsWith("update")){
            dtoBase.setCreateDate(null);
            dtoBase.setCreateUser(null);
        }

        dtoBase.setUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
        dtoBase.setUpdateUser(1L);
    }

}
