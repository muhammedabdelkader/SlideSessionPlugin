package com.slideSessionPlugin.data;

public class authTokens {
    private String watchURI = "/api/auth/oauth/token";

    private String refreshToken;
    private   String refeshTokenLable="refresh_token";

    private String clientID;
    private String cliendIDLabel="client_id";

    private String accessToken;
    private  String accessTokenLabel="access_token";

    private String activeID;
    private  String activeIDLabel ="activeId";

    private String idToken;
    private   String idTokenLabel ="id_token";

    public authTokens(String refreshToken, String clientID, String accessToken, String activeID, String idToken) {
        this.refreshToken = refreshToken;
        this.clientID = clientID;
        this.accessToken = accessToken;
        this.activeID = activeID;
        this.idToken = idToken;
    }
    public authTokens() {
        this.refreshToken = "N/A";
        this.clientID = "N/A";
        this.accessToken = "N/A";
        this.activeID = "N/A";
        this.idToken = "N/A";
    }

    public void setRefeshTokenLable(String refeshTokenLable) {
        this.refeshTokenLable = refeshTokenLable;
    }

    public void setCliendIDLabel(String cliendIDLabel) {
        this.cliendIDLabel = cliendIDLabel;
    }

    public void setAccessTokenLabel(String accessTokenLabel) {
        this.accessTokenLabel = accessTokenLabel;
    }

    public void setActiveIDLabel(String activeIDLabel) {
        this.activeIDLabel = activeIDLabel;
    }

    public void setIdTokenLabel(String idTokenLabel) {
        this.idTokenLabel = idTokenLabel;
    }

    public String getWatchURI() {
        return watchURI;
    }

    public void setWatchURI(String watchURI) {
        this.watchURI = watchURI;
    }

    public String getRefeshTokenLable() {
        return refeshTokenLable;
    }

    public String getCliendIDLabel() {
        return cliendIDLabel;
    }

    public String getAccessTokenLabel() {
        return accessTokenLabel;
    }

    public String getActiveIDLabel() {
        return activeIDLabel;
    }

    public String getIdTokenLabel() {
        return idTokenLabel;
    }

    public String getActiveID() {
        return activeID;
    }

    public void setActiveID(String activeID) {
        this.activeID = activeID;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
