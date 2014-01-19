package es.kgp.chat.server.repository;

import es.kgp.chat.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by kgp on 17/01/2014.
 */
public interface UserRepository extends JpaRepository<User, Long>{

    User findByNicknameAndPassword(String nickname, String password);

    User findByNickname(String nickname);
}
