package com.example.newmodule.component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

import static org.slf4j.LoggerFactory.getLogger;

@Aspect
@Configuration
@ConditionalOnClass(IDistributedLock.class)
@AutoConfigureAfter(DistributedLockAutoConfiguration.class)
public class DistributedLockAspectConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DistributedLockAspectConfiguration.class);

    @Autowired
    private IDistributedLock distributedLock;

    private ExpressionParser parser = new SpelExpressionParser();

    private LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    /**
     * 定义切入点
     */
    @Pointcut("@annotation(com.example.newmodule.component.DistributeLock)")
    private void lockPoint() {
    }

    /**
     * 环绕通知
     *
     * @param pjp pjp
     * @return  方法返回结果
     * @throws Throwable throwable
     */
    @Around("lockPoint()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("com.example.newmodule.component.DistributeLock");
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        DistributeLock lockAction = method.getAnnotation(DistributeLock.class);
        String logKey = getLogKey(lockAction, pjp, method);

        int retryTimes = lockAction.action().equals(DistributeLock.LockFailAction.CONTINUE) ? lockAction.retryTimes() : 0;
        boolean lock = distributedLock.lock(logKey, lockAction.keepMills(), retryTimes, lockAction.sleepMills());
        if (!lock) {
            logger.debug("get lock failed : " + logKey);
            return null;
        }

        //得到锁,执行方法，释放锁
        logger.debug("get lock success : " + logKey);
        try {
            return pjp.proceed();
        } catch (Exception e) {
            logger.error("execute locked method occured an exception", e);
        } finally {
            boolean releaseResult = distributedLock.releaseLock(logKey);
            logger.debug("release lock : " + logKey + (releaseResult ? " success" : " failed"));
        }
        return null;
    }

    /**
     * 获得分布式缓存的key
     *
     * @param lockAction 注解对象
     * @param pjp        pjp
     * @param method     method
     * @return String
     */
    private String getLogKey(DistributeLock lockAction, ProceedingJoinPoint pjp, Method method) {
        String key = lockAction.key();
        String value = lockAction.value();
        Object[] args = pjp.getArgs();
        return parse(key, method, args) + "_" + parse(value, method, args);
    }

    /**
     * 解析spring EL表达式
     *
     * @param key    key
     * @param method method
     * @param args   args
     * @return parse result
     */
    private String parse(String key, Method method, Object[] args) {
        String[] params = discoverer.getParameterNames(method);
        if (null == params || params.length == 0 || !key.contains("#")) {
            return key;
        }
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < params.length; i++) {
            context.setVariable(params[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }

}