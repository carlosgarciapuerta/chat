package es.kgp.chat.server.repository;

import es.kgp.chat.server.config.ApplicationConfig;
import es.kgp.chat.server.config.DataSourceTestConfig;
import es.kgp.chat.server.model.Chat;
import es.kgp.chat.server.model.ChatUser;
import es.kgp.chat.server.model.User;
import es.kgp.chat.server.model.UserFriend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by kgp on 18/01/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, DataSourceTestConfig.class})
@Transactional
public class UserRepositoryTest {

    public static final String USER_1 = "user1";
    public static final String PASSWORD = "password";
    public static final String USER_2 = "user2";
    public static final String USER_3 = "user3";
    public static final String NICKNAME = "nickname";

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    private User user1;
    private User user2;
    private User user3;
    private UserFriend userFriend1;
    private UserFriend userFriend2;
    private Chat chat;

    @Test
    public void should_insert_user(){
        assertEquals(userRepository.findAll().size(), 0);

        User user = new User();
        user.setNickname(NICKNAME);
        user.setPassword(PASSWORD);

        user = userRepository.save(user);
        assertEquals(user, userRepository.findOne(user.getId()));
    }

    @Test(expected = PersistenceException.class)
    public void should_throw_exception_when_nickname_null(){
        User user = new User();
        user.setPassword(PASSWORD);

        userRepository.save(user);
    }

    @Test(expected = PersistenceException.class)
    public void should_throw_exception_when_password_null(){
        User user = new User();
        user.setNickname(NICKNAME);

        userRepository.save(user);
    }

    @Test(expected = PersistenceException.class)
    public void should_throw_exception_when_inserting_existing_nickname(){
        User user = new User();
        user.setNickname(NICKNAME);
        user.setPassword(PASSWORD);

        userRepository.save(user);

        User user2 = new User();
        user2.setNickname(NICKNAME);
        user2.setPassword(PASSWORD);

        userRepository.save(user2);
    }

    @Test
    public void should_return_a_list_of_friends(){
        user1 = new User();
        user1.setNickname(USER_1);
        user1.setPassword(PASSWORD);

        user2 = new User();
        user2.setNickname(USER_2);
        user2.setPassword(PASSWORD);

        user3 = new User();
        user3.setNickname(USER_3);
        user3.setPassword(PASSWORD);

        userFriend1 = new UserFriend();
        userFriend1.setFriend(user1);
        userFriend1.setFriendOf(user2);
        userFriend1.setAccepted(true);

        userFriend2 = new UserFriend();
        userFriend2.setFriend(user1);
        userFriend2.setFriendOf(user3);
        userFriend2.setAccepted(false);

        user1.setFriends(new ArrayList<UserFriend>());
        user1.getFriends().add(userFriend1);
        user1.getFriends().add(userFriend2);

        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);
        user3 = userRepository.save(user3);

        em.persist(userFriend1);
        em.persist(userFriend2);

        assertEquals(1, userRepository.findAllFriends(user1.getId()).size());
        assertEquals(1, userRepository.findAllFriends(user2.getId()).size());
        assertEquals(0, userRepository.findAllFriends(user3.getId()).size());
    }

    @Test
    public void should_find_user_in_chat(){
        should_return_a_list_of_friends();

        createChat();

        User anotherUser1 = userRepository.findByUserInChat(user1.getId(), chat.getId());
        User anotherUser2 = userRepository.findByUserInChat(user2.getId(), chat.getId());

        assertEquals(user1, anotherUser1);
        assertEquals(user2, anotherUser2);

    }

    @Test
    public void should_return_null_on_user_not_in_chat(){
        should_return_a_list_of_friends();

        createChat();

        User anotherUser = userRepository.findByUserInChat(user3.getId(), chat.getId());

        assertNull(anotherUser);
    }

    private void createChat() {
        chat = new Chat();
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

        chat.setChatUsers(chatUsers);

        em.persist(chat);
    }


}
