package es.kgp.chat.server.service;

import es.kgp.chat.server.exception.InvalidActionException;
import es.kgp.chat.server.model.Chat;
import es.kgp.chat.server.model.User;
import es.kgp.chat.server.model.ChatUser;
import es.kgp.chat.server.model.UserFriend;
import es.kgp.chat.server.repository.ChatRepository;
import es.kgp.chat.server.repository.UserFriendRepository;
import es.kgp.chat.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kgp on 26/01/2014.
 */
@Service
public class DefaultChatService implements ChatService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserFriendRepository userFriendRepository;

    @Override
    @Transactional
    public Chat startChat(Long userId, List<Long> friendsId) {
        validateFriendsOfAUser(userId, friendsId);
        List<User> users = userRepository.findAll(friendsId);
        StringBuilder sb = new StringBuilder();
        Chat chat = new Chat();
        List<ChatUser> chatUsers = new ArrayList<>(friendsId.size() + 1);

        ChatUser chatUser = new ChatUser();
        chatUser.setUser(userRepository.findOne(userId));
        chatUser.setChat(chat);
        chatUsers.add(chatUser);
        sb.append(chatUser.getUser().getNickname()).append(", ");

        for (User user : users) {
            sb.append(user.getNickname()).append(", ");
            ChatUser userFriendChat = new ChatUser();
            userFriendChat.setUser(user);
            userFriendChat.setChat(chat);
            chatUsers.add(userFriendChat);
        }
        chat.setName(sb.toString().replaceAll(", $", ""));
        chat.setCreationDate(new Date());
        chat.setChatUsers(chatUsers);
        return chatRepository.save(chat);
    }

    @Override
    public void validateFriendsOfAUser(Long userId, List<Long> friendsId) {
        List<UserFriend> userFriends = userFriendRepository.validateFriendsForAUser(userId, friendsId);
        if (userFriends.size() != friendsId.size()){
            throw new InvalidActionException("You are not friend of all the users you requested.");
        }
    }

}
