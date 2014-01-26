package es.kgp.chat.server.repository;

import es.kgp.chat.server.model.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kgp on 17/01/2014.
 */
@Transactional
public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
}
