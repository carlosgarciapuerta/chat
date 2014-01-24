package es.kgp.chat.server.repository;

import es.kgp.chat.server.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kgp on 19/01/2014.
 */
@Transactional
public interface SessionRepository extends JpaRepository<Session, String> {

    @Modifying
    @Query("delete from Session s where s.token = :token")
    void deleteByToken(@Param("token") String token);

    Session findByToken(String token);
}
