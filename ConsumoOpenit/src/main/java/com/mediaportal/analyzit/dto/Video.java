package com.mediaportal.analyzit.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giovanny Azevedo
 */
public class Video {
    private String videoId;
    private List<Session> session = new ArrayList();

    public Video(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public List<Session> getSession() {
        return session;
    }

    public void setSession(List<Session> session) {
        this.session.addAll(session);
    }
    
    public void setSession(Session session) {
        this.session.add(session);
    }
    
}
