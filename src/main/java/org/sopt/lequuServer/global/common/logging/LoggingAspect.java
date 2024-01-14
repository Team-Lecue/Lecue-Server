package org.sopt.lequuServer.global.common.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.lang.annotation.Annotation;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Pointcut("execution(* org.sopt.lequuServer.global.exception.GlobalExceptionHandler.handleException*(..)) || execution(* org.sopt.lequuServer.global.exception.GlobalExceptionHandler.handleIllegalArgumentException*(..)) || execution(* org.sopt.lequuServer.global.exception.GlobalExceptionHandler.handleIOException*(..)) || execution(* org.sopt.lequuServer.global.exception.GlobalExceptionHandler.handleRuntimeException*(..))")
    public void errorLevelExecute() {
    }

    @Around("org.sopt.lequuServer.global.common.logging.LoggingAspect.errorLevelExecute()")
    public Object requestErrorLevelLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        long startAt = System.currentTimeMillis();
        Object returnValue = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        long endAt = System.currentTimeMillis();

        StringBuilder logMessage = new StringBuilder();
        logMessage.append("\n" +
                "- ✨ REQUEST: [" + request.getMethod() + "] " + request.getRequestURL() + "\n" +
                "- ✨ DURATION: " + (endAt - startAt) + "ms\n" +
                "- ✨ HEADERS: " + getHeaders(request) + "\n");

        Object[] args = proceedingJoinPoint.getArgs();
        if (args.length > 0) {
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            String[] paramNames = signature.getParameterNames();
            Annotation[][] paramAnnotations = signature.getMethod().getParameterAnnotations();

            for (int i = 0; i < args.length; i++) {
                for (Annotation annotation : paramAnnotations[i]) {
                    if (annotation instanceof PathVariable || annotation instanceof RequestParam) {
                        logMessage.append("- 💌 PARAMETER: " + paramNames[i] + ":" + args[i] + "\n");
                        break;
                    }
                }
            }
        }

        if ("POST".equalsIgnoreCase(request.getMethod()) || "PATCH".equalsIgnoreCase(request.getMethod())) {
            String requestBody = new String(cachingRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
            logMessage.append("- 💌 BODY: " + requestBody + "\n");
        }

        if (returnValue != null) {
            logMessage.append("- 🎁 RESPONSE: " + returnValue + "\n");
        }

        log.error(logMessage.toString());

        return returnValue;
    }

    private Map<String, Object> getHeaders(HttpServletRequest request) {
        Map<String, Object> headerMap = new HashMap<>();

        Enumeration<String> headerArray = request.getHeaderNames();
        while (headerArray.hasMoreElements()) {
            String headerName = headerArray.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }
}