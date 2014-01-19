package es.kgp.chat.server.controller;

import es.kgp.chat.server.model.Session;
import es.kgp.chat.server.security.CookieBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by kgp on 19/01/2014.
 */
@Aspect
@Component
public class SecurityInterceptor {

    @Around("execution(* es.kgp.chat.server.controller..*(..)) && args(..,session) && !@annotation(NotSecured)")
    public void security(ProceedingJoinPoint pjp, Session session){
        /*ServletRequestAttributes t = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest httpServletRequest = t.getRequest();
        RequestResponseH


        Cookie sessionToken = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if (cookie.getName().equals("sessionToken")){
                sessionToken = cookie;
                break;
            }
        }
        if (sessionToken != null){
            Session session = sessionService.refreshToken(sessionToken.getValue(), httpServletRequest.getHeader("User-Agent"));
            sessionToken = new CookieBuilder(sessionToken.getName(), session.getToken())
                    //.secured(true)
                    .withDomain(httpServletRequest.getHeader("Origin"))
                    .withMaxAge(900)
                    .withPath("/")
                    .build();
            httpServletResponse.addCookie(sessionToken);
            httpServletRequest.setAttribute("sessionToken", sessionToken);
            return true;
        } else {
            //throw new UnauthorizedException("Invalid credentials.");
            Session session = new Session();
            session.setToken("asdasdasd");
            httpServletRequest.setAttribute("sessionToken", session);
            handlerMethod.getMethodParameters()[0].getConstructor().newInstance();
            return true;
        }*/
    }

}
