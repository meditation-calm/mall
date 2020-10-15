package cn.fjcpc.manager.aop;

import cn.fjcpc.common.anotation.ServiceExeProcess;
import cn.fjcpc.common.exception.MallException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

//@Aspect
//@Component
public class ServiceExceptionAop {

    /*@Pointcut("execution(* cn.fjcpc.manager.service.ItemParamService.deleteItemParam(..)))")
    public void servicePointcut(){}

    @Around("servicePointcut()")
    public Object ServiceAnnotationAdvice(ProceedingJoinPoint point){
        Object obj;
        String error;
        try {
            obj = point.proceed();
        } catch (Throwable throwable) {
            //throwable.printStackTrace();
            error = this.getServiceExeProcessValue(point);
            System.out.println(error);
            throw new MallException("错误错误！！！");
        }
        return obj;
    }*/

    /*private String getServiceExeProcessValue(ProceedingJoinPoint point) {
        String value = "异常失败！";
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        if (method != null) {
            ServiceExeProcess annotation = method.getAnnotation(ServiceExeProcess.class);
            if (annotation != null) {
                value = annotation.value();
            }
        }
        return value;
    }*/

}
