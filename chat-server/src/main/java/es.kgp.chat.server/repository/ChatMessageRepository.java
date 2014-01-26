package es.kgp.chat.server.repository;

import es.kgp.chat.server.model.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kgp on 26/01/2014.
 */
@Repository
@Transactional
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{

    @Query("select m from ChatMessage m where m.chat.id = :chatId order by m.sent desc")
    Page<ChatMessage> findMessagesByChatId(@Param("chatId")Long chatId, Pageable pageable);

}
