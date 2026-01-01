package com.mehmetsolak.file;

public final class UserProfilePictureEvent {

    private String userId;
    private String url;

    public UserProfilePictureEvent() { }

    public UserProfilePictureEvent(String userId, String url) {
        this.userId = userId;
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
