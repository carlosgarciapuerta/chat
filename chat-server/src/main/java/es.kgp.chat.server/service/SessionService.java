package es.kgp.chat.server.service;

import es.kgp.chat.server.model.Session;
import es.kgp.chat.server.controller.security.Login;

/**
 * Created by kgp on 19/01/2014.
 */
public interface SessionService {

    public String login(Login login, String userAgent);

    public void logout(String token);

    Session validateToken(String token);

    Session refreshToken(String token, String userAgent);
}
