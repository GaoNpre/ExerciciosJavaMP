package com.mediaportal.analyzit.dto;

/**
 *
 * @author Giovanny
 */

public class User {
    private String userName;
    private String date;
    private String session;
    
    public User(String userName, String date, String session) {
        this.userName = userName;
        this.date = date;
        this.session = session;
    }
    
    public User() {
        
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setUserID(String date) {
        this.date = date;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
