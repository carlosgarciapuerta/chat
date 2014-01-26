package es.kgp.chat.server.repository;

import es.kgp.chat.server.config.ApplicationConfig;
import es.kgp.chat.server.config.DataSourceTestConfig;
import es.kgp.chat.server.model.Chat;
import es.kgp.chat.server.model.ChatUser;
import es.kgp.chat.server.model.User;
import es.kgp.chat.server.model.UserFriend;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by kgp on 26/01/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, DataSourceTestConfig.class})
@Transactional
public class ChatRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private User user1;

    private User user2;

    private User user3;

    @Before
    public void setup(){
        user1 = new User();
        user1.setNickname("nickname");
        user1.setPassword("password");

        user2 = new User();
        user2.setNickname("nickname2");
        user2.setPassword("password");

        user3 = new User();
        user3.setNickname("nickname3");
        user3.setPassword("password");

        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);
        user3 = userRepository.save(user3);

        UserFriend userFriend1 = new UserFriend();
        userFriend1.setAccepted(true);
        userFriend1.setFriend(user1);
        userFriend1.setFriendOf(user2);

        UserFriend userFriend2 = new UserFriend();
        userFriend2.setAccepted(true);
        userFriend2.setFriend(user1);
        userFriend2.setFriendOf(user3);
    }

    @Test
    public void should_insert_chat_and_the_list_of_userChat(){
        Chat chat = new Chat();
        chat.setCreationDate(new Date());
        chat.setName("testChat");

        List<ChatUser> chatUsers = new ArrayList<>();
        ChatUser chatUser1 = new ChatUser();
        chatUser1.setChat(chat);
        chatUser1.setUser(user1);
        chatUsers.add(chatUser1);

        ChatUser chatUser2 = new ChatUser();
        chatUser2.setChat(chat);
        chatUser2.setUser(user2);
        chatUsers.add(chatUser2);

        ChatUser chatUser3 = new ChatUser();
        chatUser3.setChat(chat);
        chatUser3.setUser(user3);
        chatUsers.add(chatUser3);

        chat.setChatUsers(chatUsers);

        chat = chatRepository.save(chat);

        Query query = entityManager.createQuery("select uc from ChatUser uc where uc.chat.id = :chatId", ChatUser.class);
        query.setParameter("chatId", chat.getId());
        List<ChatUser> resultList = query.getResultList();

        assertEquals(3, resultList.size());
        assertTrue(resultList.contains(chatUser1));
        assertTrue(resultList.contains(chatUser2));
        assertTrue(resultList.contains(chatUser3));

    }

}
