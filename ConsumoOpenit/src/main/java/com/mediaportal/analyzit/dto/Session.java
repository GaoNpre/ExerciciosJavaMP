package com.mediaportal.analyzit.dto;

/**
 *
 * @author Giovanny Azevedo
 */
public class Session {
    private String videoId;
    private String sessionId;
    private Integer totalSegments;
    private Integer watchedSegments;
    private String watchedPercentage;
    private Integer lastWatchedSegment;

    public Session(String sessionId, Integer totalSegments, Integer watchedSegments, String watchedPercentage, Integer lastWatchedSegment) {
        this.sessionId = sessionId;
        this.totalSegments = totalSegments;
        this.watchedSegments = watchedSegments;
        this.watchedPercentage = watchedPercentage;
        this.lastWatchedSegment = lastWatchedSegment;
    }
    
    public Session() {
        
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
    
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getTotalSegments() {
        return totalSegments;
    }

    public void setTotalSegments(Integer totalSegments) {
        this.totalSegments = totalSegments;
    }

    public Integer getWatchedSegments() {
        return watchedSegments;
    }

    public void setWatchedSegments(Integer watchedSegments) {
        this.watchedSegments = watchedSegments;
    }

    public String getWatchedPercentage() {
        return watchedPercentage;
    }

    public void setWatchedPercentage(String watchedPercentage) {
        this.watchedPercentage = watchedPercentage;
    }

    public Integer getLastWatchedSegment() {
        return lastWatchedSegment;
    }

    public void setLastWatchedSegment(Integer lastWatchedSegment) {
        this.lastWatchedSegment = lastWatchedSegment;
    }
    
}
