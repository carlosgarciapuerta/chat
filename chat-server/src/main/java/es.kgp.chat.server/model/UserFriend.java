package es.kgp.chat.server.model;

import javax.persistence.*;

/**
 * Created by kgp on 18/01/2014.
 */
@Entity
@Table(uniqueConstraints =
    @UniqueConstraint(columnNames = {"user_id", "friend_id"})
)
public class UserFriend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User friend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", nullable = false)
    private User friendOf;

    @Column(nullable = false)
    private Boolean accepted;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public User getFriendOf() {
        return friendOf;
    }

    public void setFriendOf(User friendOf) {
        this.friendOf = friendOf;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFriend)) return false;

        UserFriend that = (UserFriend) o;

        if (Id != null ? !Id.equals(that.Id) : that.Id != null) return false;
        if (accepted != null ? !accepted.equals(that.accepted) : that.accepted != null) return false;
        if (friend != null ? !friend.equals(that.friend) : that.friend != null) return false;
        if (friendOf != null ? !friendOf.equals(that.friendOf) : that.friendOf != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Id != null ? Id.hashCode() : 0;
        result = 31 * result + (friend != null ? friend.hashCode() : 0);
        result = 31 * result + (friendOf != null ? friendOf.hashCode() : 0);
        result = 31 * result + (accepted != null ? accepted.hashCode() : 0);
        return result;
    }
}
