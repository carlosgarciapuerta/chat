package es.kgp.chat.server.controller.dto;

/**
 * Created by kgp on 24/01/2014.
 */
public class FriendshipRequest {

    private Long id;

    private String friendNickName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFriendNickName() {
        return friendNickName;
    }

    public void setFriendNickName(String friendNickName) {
        this.friendNickName = friendNickName;
    }
}
