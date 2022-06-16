package burp;

import com.slideSessionPlugin.data.authTokens;
import com.slideSessionPlugin.data.loggerBook;
import com.slideSessionPlugin.gui.controlPanel;
import com.slideSessionPlugin.logic.packetInspection;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BurpExtender implements IBurpExtender, IHttpListener, ITab , IProxyListener {
    private IBurpExtenderCallbacks callbacks;
    private IExtensionHelpers helpers;
    private String extName = "SlidingSession panel";
    private String teamHttpHeader ="Team-ID: Offensive Security Team";
    private ArrayList<String> httpSlideSessionTeamHeader;
    private controlPanel cp;
    private ArrayList<loggerBook> logs;
    private PrintWriter pw;
    private packetInspection pInspect;
    private authTokens authToken;

    // Opts
    private boolean enablePInspect;
    //
    // implement IBurpExtender
    //

    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks) {
        pw = new PrintWriter(callbacks.getStdout(), false);
        // ------------------ OPT -----------------------
        enablePInspect = true;
        // --------------------------------
        // keep a reference to our callbacks object
        this.callbacks = callbacks;
        httpSlideSessionTeamHeader = new ArrayList<String>(0);
        httpSlideSessionTeamHeader.add(teamHttpHeader);
        pInspect =new packetInspection(this.callbacks);
        this.helpers = callbacks.getHelpers();
        this.callbacks.setExtensionName(extName);
        cp = new controlPanel(callbacks);
        authToken = cp.tokenObject();

        callbacks.registerHttpListener(BurpExtender.this);
        callbacks.registerProxyListener(this);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                callbacks.customizeUiComponent(cp.getMainPanel());
                // add the custom tab to Burp's UI
                callbacks.addSuiteTab(BurpExtender.this);
                logs = cp.getLogsTable().getLogTable();

            }
        });

    }
    @Override
    public void processProxyMessage(boolean messageIsRequest, IInterceptedProxyMessage message) {
        if(enablePInspect && messageIsRequest){
            pInspect.injectHTTPHeader(httpSlideSessionTeamHeader,message);

          }
    }
    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse messageInfo) {
        String watchURL=authToken.getWatchURI();

        if (toolFlag  != 0x00000004 && cp.getBoolInspectionValue()){
            if(!authToken.getAccessToken().toString().equalsIgnoreCase("N/A")) {
                messageInfo.setRequest(pInspect.updateHTTPHeader("Authorization", " Bearer " + authToken.getAccessToken(), messageInfo));
            }
        }


        if (!messageIsRequest) {

            synchronized (logs) {

                int row = logs.size();
                logs.add(new loggerBook(toolFlag, callbacks.saveBuffersToTempFiles(messageInfo), helpers.analyzeRequest(messageInfo).getUrl(), ""));
                cp.getLogsTable().insert();


            }
            URL url  = helpers.analyzeRequest(messageInfo).getUrl();

            if (url.toString().contains(watchURL)){
                String body = pInspect.getResponseBody(messageInfo);
                String refreshToken= pInspect.extractData(authToken.getRefeshTokenLable(),body);
                authToken.setAccessToken(pInspect.extractData(authToken.getAccessTokenLabel(),body));
                authToken.setRefreshToken(pInspect.extractData(authToken.getRefeshTokenLable(),body));
                updateTokens(authToken.getAccessToken(),"accessToken");
                updateTokens(authToken.getRefreshToken(),"refreshToken");
            }

        }


    }


    public void updateTokens(String value,String fieldName){
        cp.setTextFieldValue(fieldName,value);
    }
    @Override
    public String getTabCaption() {
        return extName;
    }

    @Override
    public Component getUiComponent() {
        return cp.getMainPanel();
    }
}
