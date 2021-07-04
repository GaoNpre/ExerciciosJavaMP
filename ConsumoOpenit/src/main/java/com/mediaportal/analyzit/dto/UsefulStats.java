package com.mediaportal.analyzit.dto;

/**
 *
 * @author Giovanny Azevedo
 */
public class UsefulStats {
    private String userName;
    private String date;
    private String videoId;
    private Integer totalSegments;
    private Integer watchedSegments;
    private String watchedPercentage;
    private Integer lastWatchedSegment;

    public UsefulStats(String userName, String date, String videoId, Integer totalSegments, Integer watchedSegments, String watchedPercentage, Integer lastWatchedSegment) {
        this.userName = userName;
        this.date = date;
        this.videoId = videoId;
        this.totalSegments = totalSegments;
        this.watchedSegments = watchedSegments;
        this.watchedPercentage = watchedPercentage;
        this.lastWatchedSegment = lastWatchedSegment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
