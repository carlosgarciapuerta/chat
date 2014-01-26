package es.kgp.chat.server.repository;

import es.kgp.chat.server.config.ApplicationConfig;
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

import javax.persistence.NoResultException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by kgp on 19/01/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, MySQLDataSourceConfig.class})
@Transactional
public class UserFriendRepositoryTest {

    @Autowired
    private UserFriendRepository userFriendRepository;

    @Autowired
    private UserRepository userRepository;

    private User user1;

    private User user2;

    @Before
    public void insertUsers(){
        user1 = new User();
        user1.setNickname("nickname");
        user1.setPassword("password");

        user2 = new User();
        user2.setNickname("nickname2");
        user2.setPassword("password");

        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);
    }

    @Test
    public void should_return_zero_friends(){
        UserFriend userFriend = new UserFriend();
        userFriend.setFriend(user1);
        userFriend.setFriendOf(user2);
        userFriend.setAccepted(false);
        userFriendRepository.save(userFriend);

        assertNull(userFriendRepository.findByFriendAndFriendOf(user1.getId(), user2.getId()));
    }

}
