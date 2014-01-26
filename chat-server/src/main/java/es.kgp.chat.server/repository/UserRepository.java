package es.kgp.chat.server.repository;

import es.kgp.chat.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kgp on 17/01/2014.
 */
@Transactional
public interface UserRepository extends JpaRepository<User, Long>{

    User findByNicknameAndPassword(String nickname, String password);

    User findByNickname(String nickname);

    @Query("select u from User u INNER JOIN u.friends userFriend JOIN userFriend.friend friend JOIN userFriend.friendOf friendOf where friend.id = :userId or friendOf = :userId")
    List<User> findAllFriends(@Param("userId") Long userId);
}
