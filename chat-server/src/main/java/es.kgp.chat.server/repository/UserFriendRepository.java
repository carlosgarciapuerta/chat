package es.kgp.chat.server.repository;

import es.kgp.chat.server.model.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by kgp on 17/01/2014.
 */
public interface UserFriendRepository extends JpaRepository<UserFriend, Long>, UserFriendRepositoryCustom {

    UserFriend save(UserFriend entity);

    void delete(UserFriend entity);

}
