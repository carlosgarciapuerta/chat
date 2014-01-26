package es.kgp.chat.server.controller.dto;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by kgp on 26/01/2014.
 */
public class StartChatResponse {

    private Long id;

    private String name;

    private Date creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
