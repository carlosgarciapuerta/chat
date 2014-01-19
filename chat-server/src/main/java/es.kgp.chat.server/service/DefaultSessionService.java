package es.kgp.chat.server.service;

import com.google.common.hash.Hashing;
import es.kgp.chat.server.exception.UnauthorizedException;
import es.kgp.chat.server.model.Session;
import es.kgp.chat.server.model.User;
import es.kgp.chat.server.repository.SessionRepository;
import es.kgp.chat.server.repository.UserRepository;
import es.kgp.chat.server.security.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created by kgp on 19/01/2014.
 */
@Component
public class DefaultSessionService implements SessionService{

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String login(Login login, String userAgent) {
        User user = userRepository.findByNicknameAndPassword(login.getNickname(), login.getPassword());
        if (user == null){
            throw new UnauthorizedException("Invalid credentials");
        } else {
            String token = createHashForClientSessionId(userAgent, new Date().getTime(), user.getNickname());
            Session session = new Session();
            session.setToken(token);
            session.setUser(user);
            session.setCreationTime(new Date());
            session.setExpirationTime(null);
            sessionRepository.save(session);
            return token;
        }
    }

    private String createHashForClientSessionId(String userAgent, long timestamp, String nickname) {
        return Hashing.sha256().hashString(nickname + userAgent.substring(13) + timestamp, Charset.forName("UTF-8")).toString();
    }

    @Override
    public void logout(String token) {
        sessionRepository.deleteByToken(token);
    }

    @Override
    public Session validateToken(String token){
        return sessionRepository.findByToken(token);
    }

    @Override
    public Session refreshToken(String token, String userAgent){
        Session session = validateToken(token);
        token = createHashForClientSessionId(userAgent, new Date().getTime(), session.getUser().getNickname());
        session.setToken(token);
        session.setExpirationTime(null);
        session = sessionRepository.save(session);
        return session;
    }

}
