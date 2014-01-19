package es.kgp.chat.server.controller;

import es.kgp.chat.server.exception.UnauthorizedException;
import es.kgp.chat.server.model.Session;
import es.kgp.chat.server.security.CookieBuilder;
import es.kgp.chat.server.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kgp on 19/01/2014.
 */
@Component("securityInterceptor")
public class SecurityControllerInterceptor implements HandlerInterceptor{

    @Autowired
    private SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (!handlerMethod.getMethod().isAnnotationPresent(NotSecured.class)){
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
            }
        } else{
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {

    }
}
