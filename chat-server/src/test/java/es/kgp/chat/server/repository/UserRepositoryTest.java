package es.kgp.chat.server.repository;

import es.kgp.chat.server.config.ApplicationConfig;
import es.kgp.chat.server.config.DataSourceTestConfig;
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

import static org.junit.Assert.assertEquals;

/**
 * Created by kgp on 18/01/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, DataSourceTestConfig.class})
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void should_insert_user(){
        assertEquals(userRepository.findAll().size(), 0);

        User user = new User();
        user.setNickname("nickname");
        user.setPassword("password");

        user = userRepository.save(user);
        assertEquals(user, userRepository.findOne(user.getId()));
    }

    @Test(expected = PersistenceException.class)
    public void should_throw_exception_when_nickname_null(){
        User user = new User();
        user.setPassword("password");

        userRepository.save(user);
    }

    @Test(expected = PersistenceException.class)
    public void should_throw_exception_when_password_null(){
        User user = new User();
        user.setNickname("nickname");

        userRepository.save(user);
    }

    @Test(expected = PersistenceException.class)
    public void should_throw_exception_when_inserting_existing_nickname(){
        User user = new User();
        user.setNickname("nickname");
        user.setPassword("password");

        userRepository.save(user);

        User user2 = new User();
        user2.setNickname("nickname");
        user2.setPassword("password");

        userRepository.save(user2);
    }

    @Test
    public void should_return_a_list_of_friends(){
        User user1 = new User();
        user1.setNickname("user1");
        user1.setPassword("password");

        User user2 = new User();
        user2.setNickname("user2");
        user2.setPassword("password");

        User user3 = new User();
        user3.setNickname("user3");
        user3.setPassword("password");

        UserFriend userFriend1 = new UserFriend();
        userFriend1.setFriend(user1);
        userFriend1.setFriendOf(user2);
        userFriend1.setAccepted(true);

        UserFriend userFriend2 = new UserFriend();
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



}
