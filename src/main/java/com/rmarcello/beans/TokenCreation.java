package com.rmarcello.beans;

public class TokenCreation {
    String token;
    String url;

    public TokenCreation() {
    }

    public TokenCreation(String token, String url) {
        this.token = token;
        this.url = url;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "{" +
            " token='" + getToken() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
    

}
