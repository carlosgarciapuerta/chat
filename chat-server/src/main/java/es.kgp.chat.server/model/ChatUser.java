package es.kgp.chat.server.model;

import javax.persistence.*;

/**
 * Created by kgp on 18/01/2014.
 */
@Entity
@Table(uniqueConstraints =
    @UniqueConstraint(columnNames = {"user_id", "chat_id"})
)
public class ChatUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatUser)) return false;

        ChatUser chatUser = (ChatUser) o;

        if (chat != null ? !chat.equals(chatUser.chat) : chatUser.chat != null) return false;
        if (id != null ? !id.equals(chatUser.id) : chatUser.id != null) return false;
        if (user != null ? !user.equals(chatUser.user) : chatUser.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (chat != null ? chat.hashCode() : 0);
        return result;
    }
}
