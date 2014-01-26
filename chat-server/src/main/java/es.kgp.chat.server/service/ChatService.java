package es.kgp.chat.server.service;

import es.kgp.chat.server.model.Chat;
import es.kgp.chat.server.model.ChatMessage;
import es.kgp.chat.server.model.User;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kgp on 26/01/2014.
 */
public interface ChatService {

    final static int PAGE_SIZE = 10;

    Chat startChat(Long userId, List<Long> friendsId);

    void validateFriendsOfAUser(Long userId, List<Long> friendsId);

    Page<ChatMessage> findMessages(Long chatId, int page);

    ChatMessage sendMessage(Long userId, Long chatId, String text);

    User validateUserInChat(Long userId, Long chatId);
}
