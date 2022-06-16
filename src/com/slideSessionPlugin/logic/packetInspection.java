package com.slideSessionPlugin.logic;

import com.slideSessionPlugin.json.JSONObject;
import burp.*;


import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class packetInspection {
    IBurpExtenderCallbacks callbacks;
    IExtensionHelpers helpers;
    private String customHTTPHeader;
    private  IRequestInfo info;

    public packetInspection(IBurpExtenderCallbacks calls){
        this.callbacks = calls;
        helpers = callbacks.getHelpers();
    }

    public IBurpExtenderCallbacks getCallbacks() {
        return callbacks;
    }

    public void setCallbacks(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    public IExtensionHelpers getHelpers() {
        return helpers;
    }

    public void setHelpers(IExtensionHelpers helpers) {
        this.helpers = helpers;
    }

    public String getCustomHTTPHeader() {
        return customHTTPHeader;
    }

    public void setCustomHTTPHeader(String customHTTPHeader) {
        this.customHTTPHeader = customHTTPHeader;
    }

    public void changeHeader(String operation,ArrayList<String> httpHeader, IHttpRequestResponse messageInfo){
        switch (operation){
            case "add":
                break;
            case "remove":
                break;
            case "update":
                break;
            default:
                break;
        }
    }

    public void injectHTTPHeader(ArrayList<String> customHTTPHeader, IInterceptedProxyMessage message){
        IHttpRequestResponse rrInput = message.getMessageInfo();
        info = helpers.analyzeRequest(rrInput);
        List headers = info.getHeaders();
        for (String header:customHTTPHeader
                ) {
            if (!info.getHeaders().contains(header)) {
                headers.add(header);
            }
        }
        String bodyMessage = new String(rrInput.getRequest()).substring(info.getBodyOffset());
        rrInput.setRequest(helpers.buildHttpMessage(headers, bodyMessage.getBytes()));

    }

    public void removeHTTPHeader(ArrayList<String> customHTTPHeader, IInterceptedProxyMessage message){
        IHttpRequestResponse rrInput = message.getMessageInfo();
        info = helpers.analyzeRequest(rrInput);
        List headers = info.getHeaders();
        for (String header:customHTTPHeader
                ) {
            if (info.getHeaders().contains(header)) {
                headers.remove(header);
            }
        }
        String bodyMessage = new String(rrInput.getRequest()).substring(info.getBodyOffset());
        rrInput.setRequest(helpers.buildHttpMessage(headers, bodyMessage.getBytes()));

    }


    public void deleteHTTPHeader(String customHTTPHeader, IInterceptedProxyMessage message) {
        IHttpRequestResponse rrInput = message.getMessageInfo();
        info = helpers.analyzeRequest(rrInput);
        if (info.getHeaders().contains(customHTTPHeader)) {
            List headers = info.getHeaders();
            headers.remove(customHTTPHeader);
            String bodyMessage = new String(rrInput.getRequest()).substring(info.getBodyOffset());
            rrInput.setRequest(helpers.buildHttpMessage(headers, bodyMessage.getBytes()));
        }

    }
    public byte[] updateHTTPHeader(String headerKey, String newCustomHTTPHeader, IHttpRequestResponse rrInput) {

        info = helpers.analyzeRequest(rrInput);
        List<String> headers = info.getHeaders();
        for (String header:headers
             ) {

            if(header.contains(":")) {
                 if(header.split(":")[0].toString().contains(headerKey)){
                     headers.remove(header);
                     headers.add(headerKey+": "+newCustomHTTPHeader);
                     String bodyMessage = new String(rrInput.getRequest()).substring(info.getBodyOffset());
                     return helpers.buildHttpMessage(headers, bodyMessage.getBytes());
                }
            }

        }

    return null;
    }

    private String getJsonFromBody(byte[] data, int bodyOffset){
        String body = helpers.bytesToString(data).substring(bodyOffset);
        body = body.substring(body.indexOf('{'));
        return body;
    }
    public String getResponseBody(IHttpRequestResponse requestResponse){
        int bodyOffset = helpers.analyzeRequest(requestResponse).getBodyOffset();
        byte[] response = requestResponse.getResponse();
        return getJsonFromBody(response,bodyOffset);
    }


    public String getRequestBody(IHttpRequestResponse requestResponse){
        int bodyOffset = helpers.analyzeRequest(requestResponse).getBodyOffset();
        byte[] response = requestResponse.getRequest();
        return getJsonFromBody(response,bodyOffset);
    }

    public String extractData(String memberName,String jsonData){
        byte[] jbytes = jsonData.getBytes();

        String base64Json = Base64.getEncoder().encodeToString(jbytes);
        JSONObject jsonObject =new JSONObject(new String(Base64.getDecoder().decode(base64Json)));
        if (jsonObject.has(memberName))
        {
        return jsonObject.get(memberName).toString();

        }
        return "N/A";
    }

}
