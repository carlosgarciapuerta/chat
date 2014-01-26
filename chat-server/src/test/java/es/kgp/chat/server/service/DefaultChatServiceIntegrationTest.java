package es.kgp.chat.server.service;

import es.kgp.chat.server.config.ApplicationConfig;
import es.kgp.chat.server.config.DataSourceTestConfig;
import es.kgp.chat.server.exception.InvalidActionException;
import es.kgp.chat.server.model.Chat;
import es.kgp.chat.server.model.User;
import es.kgp.chat.server.model.UserFriend;
import es.kgp.chat.server.repository.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by kgp on 26/01/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, DataSourceTestConfig.class})
@Transactional
public class DefaultChatServiceIntegrationTest {

    public static final String USER_1 = "user1";
    public static final String USER_3 = "user3";
    public static final String USER_2 = "user2";
    public static final String PASSWORD = "password";

    @Autowired
    private ChatService chatService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User user1;

    private User user2;

    private User user3;

    @Before
    public void setup(){
        user1 = new User();
        user1.setNickname(USER_1);
        user1.setPassword(PASSWORD);

        user2 = new User();
        user2.setNickname(USER_2);
        user2.setPassword(PASSWORD);

        user3 = new User();
        user3.setNickname(USER_3);
        user3.setPassword(PASSWORD);

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(user3);

        UserFriend userFriend1 = new UserFriend();
        userFriend1.setFriend(user1);
        userFriend1.setFriendOf(user2);
        userFriend1.setAccepted(true);

        UserFriend userFriend2 = new UserFriend();
        userFriend2.setFriend(user3);
        userFriend2.setFriendOf(user1);
        userFriend2.setAccepted(true);

        entityManager.persist(userFriend1);
        entityManager.persist(userFriend2);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void should_throw_exception_when_user_is_not_friend_of_all_users(){
        expectedException.expect(InvalidActionException.class);
        expectedException.expectMessage("You are not friend of all the users you requested.");

        List<Long> friendsId = new ArrayList<>();
        friendsId.add(userRepository.findByNickname(USER_1).getId());
        friendsId.add(userRepository.findByNickname(USER_3).getId());

        chatService.validateFriendsOfAUser(userRepository.findByNickname(USER_2).getId(), friendsId);
    }

    @Test
    public void should_validate_user_and_the_friends(){
        List<Long> friendsId = new ArrayList<>();
        friendsId.add(userRepository.findByNickname(USER_2).getId());
        friendsId.add(userRepository.findByNickname(USER_3).getId());

        chatService.validateFriendsOfAUser(userRepository.findByNickname(USER_1).getId(), friendsId);
    }

    @Test
    public void should_create_a_chat(){
        List<Long> friendsId = new ArrayList<>();
        friendsId.add(userRepository.findByNickname(USER_2).getId());
        friendsId.add(userRepository.findByNickname(USER_3).getId());

        Chat chat = chatService.startChat(userRepository.findByNickname(USER_1).getId(), friendsId);

        assertEquals(user1.getNickname() + ", " + user2.getNickname() + ", " + user3.getNickname(), chat.getName());
        assertEquals(3, chat.getChatUsers().size());
    }

}
