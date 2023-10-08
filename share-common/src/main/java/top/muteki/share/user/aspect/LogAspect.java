package top.muteki.share.user.aspect;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Aspect
@Component
@Slf4j
public class LogAspect {
    public LogAspect(){
        log.info("logAspect");
    }
    @Pointcut("execution(public * top.muteki..*Controller.*(..))")
    public void controllerPointcut(){

    }
    @Before("controllerPointcut()")
    public void doBefore(JoinPoint joinPoint){
//        MDC.put("LOG_ID",System.currentTimeMillis()+ RandomUtil.randomString(3));
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes !=null;
        HttpServletRequest request= attributes.getRequest();
        Signature signature=joinPoint.getSignature();
        String name=signature.getName();
        log.info("============");
        log.info("请求地址： {} {}",request.getRequestURI().toString(),request.getMethod());
        log.info("类名方法: {}{}",signature.getDeclaringTypeName(),name);
        log.info("远程地址: {}",request.getRemoteAddr());
        Object[] args=joinPoint.getArgs();
        Object[] arguments=new Object[args.length];
        for (int i=0;i<args.length;i++){
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile){
                continue;
            }
            arguments[i]=args[i];
        }
        String[] excludeProperties = {"phone","password"};
        PropertyPreFilters filters=new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = filters.addFilter();
        excludeFilter.addExcludes(excludeProperties);
        log.info("请求参数: {}",JSONObject.toJSONString(arguments,excludeFilter));
    }
    @Around("controllerPointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
        long startTime=System.currentTimeMillis();
        Object result=proceedingJoinPoint.proceed();
        String[] excludeProperties= {"phone","password"};
        PropertyPreFilters filters=new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = filters.addFilter();
        excludeFilter.addExcludes(excludeProperties);
        log.info("返回结果:{}",JSONObject.toJSONString(result,excludeFilter));
        log.info("=======结束 耗时 {}ms=========",System.currentTimeMillis()-startTime);
        return result;
    }

}
