package es.kgp.chat.server.service;

import es.kgp.chat.server.model.User;
import es.kgp.chat.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kgp on 23/01/2014.
 */
@Service
public class DefaultFriendsService implements FriendsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAllFriends(Long userId) {
        return userRepository.findAllFriends(userId);
    }
}
