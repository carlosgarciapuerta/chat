package es.kgp.chat.server.security;

import javax.servlet.http.Cookie;

/**
 * Created by kgp on 19/01/2014.
 */
public class CookieBuilder {

    private Cookie cookie;

    public CookieBuilder(String name, String value){
        cookie = new Cookie(name, value);
    }

    public CookieBuilder withPath(String path){
        cookie.setPath(path);
        return this;
    }

    public CookieBuilder secured(boolean secured){
        cookie.setSecure(secured);
        return this;
    }

    public CookieBuilder withDomain(String domain){
        cookie.setDomain(domain);
        return this;
    }

    public CookieBuilder withMaxAge(int maxAge){
        cookie.setMaxAge(maxAge);
        return this;
    }

    public Cookie build(){
        return cookie;
    }

}
