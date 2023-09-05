package com.company.app.core.aop.logging.performance.component.action.impl;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.core.aop.logging.performance.component.action.PerformanceLogAbstractAction;
import com.company.app.core.aop.logging.performance.component.PerformanceLogActionType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PerformanceLogNumberAction extends PerformanceLogAbstractAction {

    @Override
    public PerformanceLogActionType getType() {
        return PerformanceLogActionType.NUMBER;
    }

    @Override
    public String getGuid(ProceedingJoinPoint proceedingJoinPoint, PerformanceLogAnnotation annotation) {
        return getGuidBySignatureParameter(proceedingJoinPoint, annotation.number());
    }

    private String getGuidBySignatureParameter(ProceedingJoinPoint proceedingJoinPoint, String number) {
        Object originalObjectFromSignature = reflector.getArg(proceedingJoinPoint, number);
        UUID uuid = UUID.fromString(String.valueOf(originalObjectFromSignature));
        return uuid.toString();
    }

}
