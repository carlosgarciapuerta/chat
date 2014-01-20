package es.kgp.chat.server.controller.security;

/**
 * Created by kgp on 19/01/2014.
 */
public class Login {

    private String nickname;
    private String password;

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
}
