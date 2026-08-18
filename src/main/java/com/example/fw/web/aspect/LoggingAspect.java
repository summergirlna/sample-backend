package com.example.fw.web.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

  @Around("execution(* com.example.backend..*(..))")
  public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
    String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
    String methodName = joinPoint.getSignature().getName();

    log.info("処理を開始します。クラス = {}, メソッド =  {}", className, methodName);

    try {
      Object result = joinPoint.proceed();

      log.info("処理が正常終了しました。クラス = {}, メソッド = {}", className, methodName);
      return result;
    } catch (Throwable e) {
      log.error(
          "処理が異常終了しました。クラス = {}, メソッド = {}, メッセージ = {}", className, methodName, e.getMessage());
      throw e;
    }
  }
}
