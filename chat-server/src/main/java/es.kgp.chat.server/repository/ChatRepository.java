package es.kgp.chat.server.repository;

import es.kgp.chat.server.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by kgp on 17/01/2014.
 */
public interface ChatRepository extends JpaRepository<Chat, Long> {
}
