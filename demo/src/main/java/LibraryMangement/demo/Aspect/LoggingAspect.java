package LibraryMangement.demo.Aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
public final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut expression for methods related to book operations
    @Pointcut("execution(* LibraryMangement.demo.Service.BookServiceImpl.*(..))")
    private void bookOperationsPointcut() {}

    // Pointcut expression for methods related to patron operations
    @Pointcut("execution(* LibraryMangement.demo.Service.PatronServiceImpl.*(..))")
    private void patronOperationsPointcut() {}

    // Advice method to log method calls before execution
    @Around("bookOperationsPointcut() || patronOperationsPointcut()")
    public Object logMethodCallAndPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();
        System.out.println("Method called: " + methodName);

        Object result;
        Object[] methodArgs = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();

        for (int i = 0; i < methodArgs.length; i++) {
            log.info("Parameter {}: {} = {}", i, parameterNames[i], methodArgs[i]);
        }

        try {
            // Proceed with the actual method execution
            result = joinPoint.proceed();


        } catch (Throwable throwable) {
            // Handle exceptions if needed
            System.out.println("Exception in method " + methodName + ": " + throwable.getMessage());
            throw throwable;
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Method execution time: " + elapsedTime + " ms");

        return result;
    }    // Advice method to log exceptions after throwing
    @AfterThrowing(pointcut = "bookOperationsPointcut() || patronOperationsPointcut()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Exception in method " + methodName + ": " + ex.getMessage());
    }
}
