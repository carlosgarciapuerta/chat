package es.kgp.chat.server.repository;

import es.kgp.chat.server.model.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kgp on 17/01/2014.
 */
@Transactional
public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {

    @Query("select uf from UserFriend uf where uf.friend.id = :friendId and uf.friendOf.id = :friendOfId and uf.accepted = true")
    UserFriend findByFriendAndFriendOf(@Param("friendId")Long friendId, @Param("friendOfId") Long friendOfId);

}
