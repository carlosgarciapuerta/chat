package es.kgp.chat.server.repository;

import es.kgp.chat.server.config.ApplicationConfig;
import es.kgp.chat.server.config.DataSourceTestConfig;
import es.kgp.chat.server.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;

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



}
