package com.slideSessionPlugin.data;


import burp.IHttpRequestResponsePersisted;

import java.net.URL;

public class loggerBook {
    public final int toolFlag;
    public final URL url;
    public final String token;
    private final IHttpRequestResponsePersisted requestResponse;
    private final int columnCounts = 3;

    public loggerBook() {
        // Nothing is required
        this.url = null;
        this.toolFlag = -1;
        this.token = null;
        this.requestResponse = null;
    }

    public loggerBook(int toolFlag, IHttpRequestResponsePersisted requestResponse, URL url, String token) {
        this.url = url;
        this.toolFlag = toolFlag;
        this.token = token;
        this.requestResponse = requestResponse;

    }

    public IHttpRequestResponsePersisted getRR() {
        return this.requestResponse;
    }

    public URL getUrl() {
        return this.url;
    }

    public String getToken() {
        return this.token;
    }

    public int getColumnCounts() {
        return columnCounts;
    }

    public String getColumnName(int index) {
        switch (index) {
            case 0:
                return "Tool";
            case 1:
                return "URL";
            case 2:
                return "Token";
            default:
                return "Undefined Value in Logger";
        }
    }

}
