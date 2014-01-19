package es.kgp.chat.server.controller;

import es.kgp.chat.server.security.CookieBuilder;
import es.kgp.chat.server.security.Login;
import es.kgp.chat.server.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by kgp on 19/01/2014.
 */
@Controller
public class LoginController {

    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @NotSecured
    public String login(@RequestBody @Valid Login login, @RequestHeader("User-Agent") String userAgent, @RequestHeader("Origin") String origin, HttpServletResponse response){
        String token = sessionService.login(login, userAgent);
        Cookie sessionToken = new CookieBuilder("sessionToken", token)
            //.secured(true)
            .withDomain("*")
            .withMaxAge(900)
            .withPath("/")
            .build();

        response.addCookie(sessionToken);
        return "1234567890";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@CookieValue("sessionToken") String token){
        sessionService.logout(token);
    }

}
