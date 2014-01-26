package es.kgp.chat.server.repository;

import es.kgp.chat.server.config.ApplicationConfig;
import es.kgp.chat.server.config.DataSourceTestConfig;
import es.kgp.chat.server.config.MySQLDataSourceConfig;
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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kgp on 19/01/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, DataSourceTestConfig.class})
@Transactional
public class UserFriendRepositoryTest {

    @Autowired
    private UserFriendRepository userFriendRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private User user1;

    private User user2;

    @Before
    public void setup(){
        user1 = new User();
        user1.setNickname("nickname");
        user1.setPassword("password");

        user2 = new User();
        user2.setNickname("nickname2");
        user2.setPassword("password");

        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);
    }

    private UserFriend insertUserFriend() {
        UserFriend userFriend = new UserFriend();
        userFriend.setFriend(user1);
        userFriend.setFriendOf(user2);
        userFriend.setAccepted(false);
        return userFriendRepository.save(userFriend);
    }

    @Test
    public void should_return_zero_friends(){
        insertUserFriend();

        assertNotNull(userFriendRepository.findByFriendAndFriendOf(user1.getId(), user2.getId()));
    }

    @Test
    public void should_update_to_accepted_friend_request(){
        UserFriend friendRequest = insertUserFriend();
        userFriendRepository.updateFriendRequest(friendRequest.getId(), true);
        UserFriend updatedFriendRequest = userFriendRepository.findOne(friendRequest.getId());

        assertTrue(updatedFriendRequest.getAccepted());
    }

    @Test
    public void should_validate_friends_of_a_user(){
        UserFriend userFriend = insertUserFriend();
        List<Long> friendsId = new ArrayList<>();
        friendsId.add(user2.getId());
        List<UserFriend> userFriends = userFriendRepository.validateFriendsForAUser(user1.getId(), friendsId);

        List<Long> friendsIdSymmetric = new ArrayList<>();
        friendsIdSymmetric.add(user1.getId());
        List<UserFriend> userFriendsSymmetric = userFriendRepository.validateFriendsForAUser(user2.getId(), friendsIdSymmetric);

        assertEquals(1, userFriends.size());
        assertEquals(userFriend, userFriends.get(0));

        assertEquals(1, userFriendsSymmetric.size());
        assertEquals(userFriend, userFriendsSymmetric.get(0));
    }

}
