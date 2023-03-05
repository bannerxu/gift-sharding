package top.banner.config.log;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import top.banner.log.TraceWatch;

import java.lang.reflect.Method;

public class LogAdvice implements MethodInterceptor {


    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        Method method = methodInvocation.getMethod();
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        final TraceWatch<?> traceWatch = new TraceWatch<>(Class.forName(className));

        traceWatch.start(methodName);
        Object result = methodInvocation.proceed();
        traceWatch.stop(className + "." + methodName + "()");

        return result;
    }

}