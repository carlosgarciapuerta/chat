package es.kgp.chat.server.service;

import es.kgp.chat.server.model.User;
import es.kgp.chat.server.model.UserFriend;
import es.kgp.chat.server.repository.UserFriendRepository;
import es.kgp.chat.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kgp on 23/01/2014.
 */
@Service
@Transactional(readOnly = true)
public class DefaultFriendsService implements FriendsService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFriendRepository userFriendRepository;

    @Override
    public List<User> findAllFriends(Long userId) {
        return userRepository.findAllFriends(userId);
    }

    @Override
    @Transactional(readOnly = false)
    public UserFriend sendFriendRequest(Long userId, String friendNickname) {
        User friend = userRepository.findByNickname(friendNickname);
        User user = userRepository.findOne(userId);
        UserFriend friendRequest = new UserFriend();
        friendRequest.setAccepted(false);
        friendRequest.setFriend(user);
        friendRequest.setFriendOf(friend);
        return userFriendRepository.save(friendRequest);
    }

    @Override
    public void acceptFriendRequest(Long friendRequestId) {
        userFriendRepository.updateFriendRequest(friendRequestId, true);
    }

    @Override
    public void cancellFriendRequest(Long friendRequestId) {
        userFriendRepository.delete(friendRequestId);
    }
}
