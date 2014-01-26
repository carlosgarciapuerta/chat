package es.kgp.chat.server.service;

import es.kgp.chat.server.config.ApplicationConfig;
import es.kgp.chat.server.config.DataSourceTestConfig;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by kgp on 26/01/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, DataSourceTestConfig.class})
@Transactional
public class DefaultFriendsServiceIntegrationTest {

    @Autowired
    private FriendsService friendsService;

    @PersistenceContext
    private EntityManager entityManager;

    private User user1;

    private User user2;

    @Before
    public void setup(){
        user1 = new User();
        user1.setNickname("user1");
        user1.setPassword("password");

        user2 = new User();
        user2.setNickname("user2");
        user2.setPassword("password");

        entityManager.persist(user1);
        entityManager.persist(user2);
    }

    @Test
    public void should_insert_friendRequest() {
        UserFriend friendRequest = friendsService.sendFriendRequest(user1.getId(), user2.getNickname());
        assertNotNull(friendRequest);

        assertEquals(user1, friendRequest.getFriend());
        assertEquals(user2, friendRequest.getFriendOf());
        assertFalse(friendRequest.getAccepted());

    }

    private UserFriend insertUserFriend() {
        UserFriend userFriend = new UserFriend();
        userFriend.setAccepted(false);
        userFriend.setFriend(user1);
        userFriend.setFriendOf(user2);
        entityManager.persist(userFriend);
        return userFriend;
    }

    @Test
    public void should_accept_friend_request(){
        UserFriend userFriend = insertUserFriend();

        friendsService.acceptFriendRequest(userFriend.getId());

        UserFriend updatedFriendRequest = entityManager.find(UserFriend.class, userFriend.getId());
        entityManager.refresh(updatedFriendRequest);

        assertTrue(updatedFriendRequest.getAccepted());
    }

    @Test
    public void should_cancell_friend_request(){
        UserFriend userFriend = insertUserFriend();

        friendsService.cancellFriendRequest(userFriend.getId());

        UserFriend updatedFriendRequest = entityManager.find(UserFriend.class, userFriend.getId());

        assertNull(updatedFriendRequest);
    }

}
