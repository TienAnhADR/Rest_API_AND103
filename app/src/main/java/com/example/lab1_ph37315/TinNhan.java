package com.example.lab1_ph37315;

import java.util.HashMap;

public class TinNhan {
    private String user;
    private String content;
    private String id;

    public TinNhan(String user, String content, String id) {
        this.user = user;
        this.content = content;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TinNhan(String user, String content) {
        this.user = user;
        this.content = content;
    }

    public TinNhan() {
    }
    public HashMap<String,Object> convertTinNhan(){
        HashMap<String,Object> h = new HashMap<>();
        h.put("id",id);
        h.put("userId",user);
        h.put("content",content);
        return h;
    }

}
