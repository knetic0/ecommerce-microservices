package com.mehmetsolak.email;

public final class WelcomeEvent {
    private String to;
    private String fullName;

    public WelcomeEvent() { }

    public WelcomeEvent(String to, String fullName) {
        this.to = to;
        this.fullName = fullName;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
