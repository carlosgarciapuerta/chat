package es.kgp.chat.server.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by kgp on 18/01/2014.
 */
//@Entity
public class BlockedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private User userBlocked;

}
