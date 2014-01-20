package es.kgp.chat.server.controller.security;

import es.kgp.chat.server.model.Session;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Created by kgp on 19/01/2014.
 */
@Aspect
@Component
public class SecurityControllerAspect {

    @Around("@within(org.springframework.stereotype.Controller) && !@annotation(es.kgp.chat.server.controller.security.NotSecured)")
    public Object security(ProceedingJoinPoint pjp) throws Throwable {
        Session session = (Session) RequestContextHolder.currentRequestAttributes().getAttribute("session", RequestAttributes.SCOPE_REQUEST);
        Object[] args = pjp.getArgs();
        Object[] newArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Session){
                newArgs[i] = session;
            } else {
                newArgs[i] = args[i];
            }
        }
        return pjp.proceed(newArgs);
    }

}
