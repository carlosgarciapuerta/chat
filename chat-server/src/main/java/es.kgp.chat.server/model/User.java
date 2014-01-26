package es.kgp.chat.server.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by kgp on 18/01/2014.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String nickname;
    @Column(nullable = false, length = 20)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "friend")
    private List<UserFriend> friends;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "friendOf")
    private List<UserFriend> friendsOf;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<ChatUser> chatUsers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserFriend> getFriends() {
        return friends;
    }

    public void setFriends(List<UserFriend> friends) {
        this.friends = friends;
    }

    public List<UserFriend> getFriendsOf() {
        return friendsOf;
    }

    public void setFriendsOf(List<UserFriend> friendsOf) {
        this.friendsOf = friendsOf;
    }

    public List<ChatUser> getChatUsers() {
        return chatUsers;
    }

    public void setChatUsers(List<ChatUser> chatUsers) {
        this.chatUsers = chatUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
