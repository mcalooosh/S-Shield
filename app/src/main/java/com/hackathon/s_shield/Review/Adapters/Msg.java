package com.hackathon.s_shield.Review.Adapters;

/**
 * Created by Aloush on 1/28/2017.
 */
public class Msg {

    private String username,message;
    boolean me;

    public Msg(String username, String message, boolean me){
        this.username=username;
        this.message=message;
        this.me=me;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMe() {
        return me;
    }

    public void setMe(boolean me) {
        this.me = me;
    }
}