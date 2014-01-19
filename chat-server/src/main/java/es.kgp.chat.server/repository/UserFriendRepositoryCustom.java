package es.kgp.chat.server.repository;

import es.kgp.chat.server.model.UserFriend;

/**
 * Created by kgp on 18/01/2014.
 */
public interface UserFriendRepositoryCustom {

    UserFriend findByFriendAndFriendOf(Long friendId, Long friendOfId);

    UserFriend save(UserFriend entity);

    void delete(UserFriend entity);

}
