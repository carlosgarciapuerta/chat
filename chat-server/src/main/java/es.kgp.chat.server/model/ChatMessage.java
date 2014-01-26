package es.kgp.chat.server.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by kgp on 18/01/2014.
 */
@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatMessage)) return false;

        ChatMessage chatMessage = (ChatMessage) o;

        if (id != null ? !id.equals(chatMessage.id) : chatMessage.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
