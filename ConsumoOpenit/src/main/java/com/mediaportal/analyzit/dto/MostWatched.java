package com.mediaportal.analyzit.dto;

/**
 *
 * @author Giovanny Azevedo
 */
public class MostWatched {
    private String videoId;
    private Integer views;
    
    public MostWatched(String videoId, Integer views) {
        this.videoId = videoId;
        this.views = views;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }
    
    
}
