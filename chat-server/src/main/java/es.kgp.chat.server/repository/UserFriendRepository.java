package es.kgp.chat.server.repository;

import es.kgp.chat.server.model.UserFriend;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kgp on 17/01/2014.
 */
@Transactional
public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {

    @Query("select uf from UserFriend uf where uf.friend.id = :friendId and uf.friendOf.id = :friendOfId")
    UserFriend findByFriendAndFriendOf(@Param("friendId")Long friendId, @Param("friendOfId") Long friendOfId);

    @Modifying(clearAutomatically = true)
    @Query("update UserFriend uf1 set uf1.accepted = :accepted where uf1 in (select uf from UserFriend uf where (uf.friend.id = :userId and uf.friendOf.nickname = :nickname) or (uf.friendOf.id = :userId and uf.friend.nickname = :nickname))")
    void updateFriendRequest(@Param("userId") Long userId, @Param("nickname") String friendNickName, @Param("accepted") boolean accepted);

    @Query("select uf from UserFriend uf where (uf.friend.id = :userId and uf.friendOf.nickname in (:friendsNickname)) or (uf.friend.nickname in (:friendsNickname) and uf.friendOf.id = :userId)")
    List<UserFriend> validateFriendsForAUser(@Param("userId") Long userId, @Param("friendsNickname") List<String> friendsNickname);

    @Modifying(clearAutomatically = true)
    @Query("delete UserFriend uf1 where uf1 in (select uf from UserFriend uf where (uf.friend.id = :userId and uf.friendOf.nickname = :nickname) or (uf.friendOf.id = :userId and uf.friend.nickname = :nickname))")
    void delete(@Param("userId") Long userId, @Param("nickname") String friendNickName);
}
