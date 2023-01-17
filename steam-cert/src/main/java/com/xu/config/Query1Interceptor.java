package com.xu.config;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

@Intercepts(
        {@Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(
                        type = Executor.class,
                        method = "query",
                        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
                @Signature(
                        type = Executor.class,
                        method = "update",
                        args = {MappedStatement.class, Object.class}),

        })
public class Query1Interceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(Query1Interceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatements = (MappedStatement) args[0];
        Map<String, Object> paramMap = new MapperMethod.ParamMap<>();
        if (args[1] != null) {
            if (args[1] instanceof MapperMethod.ParamMap) {
                paramMap.putAll((Map) args[1]);
            } else if (BeanUtils.isSimpleValueType(args[1].getClass())) {
                String mapperId = mappedStatements.getId();
                Class<?> paramType = args[1].getClass();
                String clazzName = mapperId.substring(0, mapperId.lastIndexOf("."));
                String methodName = mapperId.substring(mapperId.lastIndexOf(".") + 1);
                Class<?> target = Class.forName(clazzName);
                Method method = target.getMethod(methodName, paramType);
                Parameter[] params = method.getParameters();
                String paramName = params[0].getName();

                paramMap.put(paramName, args[1]);
            } else {
                List<Field> fields = new ArrayList<>();
                getAllFields(fields, args[1].getClass());

                for (Field field : fields) {
                    Object fieldValue = null;
                    String fieldName = field.getName();
                    try {
                        fieldValue = (new PropertyDescriptor(fieldName, args[1].getClass())).getReadMethod().invoke(args[1]);
                    } catch (IllegalArgumentException var9) {
                        logger.error(var9.getMessage(), var9);
                    } catch (IllegalAccessException var10) {
                        logger.error(var10.getMessage(), var10);
                    } catch (InvocationTargetException var11) {
                        logger.error(var11.getMessage(), var11);
                    } catch (IntrospectionException var12) {
                        logger.debug(var12.getMessage(), var12);
                    } catch (Exception var13) {
                        logger.debug(var13.getMessage(), var13);
                    }
                    paramMap.putIfAbsent(field.getName(), fieldValue);
                }
            }
        }

        paramMap.put("schemeId", "public");
        args[1] = paramMap;
        return invocation.proceed();

    }

    private List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            this.getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        if(properties!=null){

        }
    }
}
