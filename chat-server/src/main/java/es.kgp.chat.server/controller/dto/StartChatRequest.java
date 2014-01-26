package es.kgp.chat.server.controller.dto;

import java.util.List;

/**
 * Created by kgp on 26/01/2014.
 */
public class StartChatRequest {

    private List<String> friendsNickname;

    public List<String> getFriendsNickname() {
        return friendsNickname;
    }

    public void setFriendsNickname(List<String> friendsNickname) {
        this.friendsNickname = friendsNickname;
    }
}
