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

    @Query("select u from User u INNER JOIN u.friends userFriend JOIN userFriend.friend friend JOIN userFriend.friendOf friendOf where (friend.id = :userId or friendOf = :userId) and userFriend.accepted = :accepted")
    List<User> findAllFriends(@Param("userId") Long userId, @Param("accepted") boolean accepted);

    @Query("select u from Chat c JOIN c.chatUsers cu JOIN cu.user u where u.id = :userId and c.id = :chatId")
    User findByUserInChat(@Param("userId") Long userId, @Param("chatId") Long chatId);

    @Query("select u from User u where u.nickname in (:friendsNickname)")
    List<User> findAll(@Param("friendsNickname") List<String> friendsNickname);
}
