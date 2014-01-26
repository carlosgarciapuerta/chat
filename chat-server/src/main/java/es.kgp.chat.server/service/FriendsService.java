package es.kgp.chat.server.service;

import es.kgp.chat.server.model.User;
import es.kgp.chat.server.model.UserFriend;

import java.util.List;

/**
 * Created by kgp on 23/01/2014.
 */
public interface FriendsService {

    List<User> findAllFriends(Long userId);

    public UserFriend sendFriendRequest(Long userId, String friendNickname);

    public void acceptFriendRequest(Long friendRequestId);

    public void cancellFriendRequest(Long friendRequestId);

}
