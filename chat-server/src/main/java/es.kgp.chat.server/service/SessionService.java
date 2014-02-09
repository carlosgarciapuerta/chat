package es.kgp.chat.server.service;

import es.kgp.chat.server.model.Session;
import es.kgp.chat.server.controller.dto.Login;
import es.kgp.chat.server.model.User;

/**
 * Created by kgp on 19/01/2014.
 */
public interface SessionService {

    public static final String SESSION_TOKEN = "sessionCookie";

    public String login(Login login, String userAgent);

    public void logout(String token);

    Session validateToken(String token);

    Session refreshToken(String token, String userAgent);

    void register(User user, String userAgent);

}
