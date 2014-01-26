package es.kgp.chat.server.service;

import es.kgp.chat.server.model.Chat;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kgp on 26/01/2014.
 */
public interface ChatService {

    Chat startChat(Long userId, List<Long> friendsId);

    void validateFriendsOfAUser(Long userId, List<Long> friendsId);

}
