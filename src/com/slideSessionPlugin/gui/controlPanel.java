package com.slideSessionPlugin.gui;

import burp.IBurpExtenderCallbacks;
import com.slideSessionPlugin.data.authTokens;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class controlPanel {
    private final String requestLabelTab = "Request";
    private final String responseLabelTab = "Response";
    private final String logLAbelTab = "Logs";
    private final String tokenPanelTab = "Tokens Panel";
    private final String settingsPanelTab = "Settings";
    private final String tokenConfigurationBorderTitle = "Configurations";
    private final String inspectionConfigurationBorderTitle = "Inspection Settings";

    private boolean boolInspectionValue;
    private IBurpExtenderCallbacks callbacks;
    // Main Panel
    private JTabbedPane mainPanel;


    // Settings Panel
    // Todo settings panel

    // Token Panel
    private JPanel tokenPanel, tokenConfigurationPanel, tokenInspectionPanel;
    private JSplitPane tokenConfigurationSplitPanel;
    private JLabel refTokenLabel, accessTokenLabel, inspectionURILAbel, inspectionAccessTokenMemberName, inspectionRefreshTokenMemeberName;
    private JTextField refTokenField, accessTokenField, inspectionURITextField, inspectionAccessTokenTextField, inspectionRefreshTokenTextField;
    private JButton resetButton, updateButton, setValues , inspectionSettingsSubmit;
    private authTokens authToken;
    private  enum tokenConfigurationLabels{
        Packet_Inspection, Paranoia
    }



    // Logs Panel
    // private  JSplitPane logsPanel;
    private logsTable logsTable;
    private JTextArea request, response;
    private JScrollPane scrollPane;
    private JSplitPane splitpane;
    private JTabbedPane requestResponsePanel;
    
    

    public controlPanel(IBurpExtenderCallbacks callbacks) {

        authToken = new authTokens();
        this.callbacks = callbacks;
        init();
        setNames();
        configureComponents();
        setAligment();
        enableComponents();
        addActions();
        packtokenPanelComponents();
}


    public void init() {
        boolInspectionValue = false;
        tokenPanel = new JPanel();
        logsTable = new logsTable(this.callbacks);
        mainPanel = new JTabbedPane();
        refTokenField = new JTextField();
        accessTokenField = new JTextField();
    }
    public authTokens tokenObject(){
        return authToken;
    }
    public void setNames() {

        // Setup Control Panel
        tokenConfigurationAreaInit();
        tokenConfigurationSplitPanel =new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        initInspectionSettings();

        refTokenLabel = new JLabel("refresh_token");
        accessTokenLabel = new JLabel("access_token");
        resetButton = new JButton("Reset");
        updateButton = new JButton("Update Tools");
        setValues = new JButton("Set Values");

        tokenConfigurationPanel.add(tokenInspectionPanel);
        tokenConfigurationSplitPanel.setRightComponent(tokenConfigurationPanel);
        tokenConfigurationSplitPanel.setLeftComponent(tokenPanel);

        tokenConfigurationSplitPanel.setDividerLocation(1000);

        // Add Token Tab
        mainPanel.addTab(tokenPanelTab, tokenConfigurationSplitPanel);

        // Setup logger Area

        request = new JTextArea();
        request.setEditable(false);
        response = new JTextArea();
        response.setEditable(false);
        logsTable.setReTextArea(request);
        logsTable.setResTextArea(response);
        scrollPane = new JScrollPane(logsTable.showTable());
        splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitpane.setLeftComponent(scrollPane);

        requestResponsePanel = new JTabbedPane();
        requestResponsePanel.addTab(requestLabelTab, request);
        requestResponsePanel.addTab(responseLabelTab, response);
        splitpane.setRightComponent(requestResponsePanel);
        mainPanel.addTab(logLAbelTab, splitpane);
    }
    private void setBoolInspectionValue(boolean value) {
        boolInspectionValue = value;
    }

    public boolean getBoolInspectionValue(){
        return boolInspectionValue;
    }
    public void setRequest(String Text) {
        request.setText(Text);
    }

    public void setResponse(String Text) {
        response.setText(Text);
    }

    public JTabbedPane getMainPanel() {
        return mainPanel;
    }

    public logsTable getLogsTable() {
        return logsTable;
    }

    public void initInspectionSettings(){

        tokenInspectionPanel = new JPanel();
        tokenInspectionPanel.setLayout(null);

        inspectionURITextField = new JTextField();
        inspectionAccessTokenTextField = new JTextField();
        inspectionRefreshTokenTextField = new JTextField();

        inspectionURITextField.setEnabled(boolInspectionValue);
        inspectionAccessTokenTextField.setEnabled(boolInspectionValue);
        inspectionRefreshTokenTextField.setEnabled(boolInspectionValue);

        inspectionAccessTokenMemberName=new JLabel("Access Token Member Name");
        inspectionRefreshTokenMemeberName=new JLabel("Refresh Token Member Name");
        inspectionURILAbel=new JLabel("URI");

        inspectionSettingsSubmit = new JButton("Apply Changes");
        inspectionSettingsSubmit.setBounds(240,250,150,40);

        inspectionURILAbel.setBounds(20, 50, 100, 20);
        inspectionURITextField.setBounds(240, 50, 300, 40);

        inspectionAccessTokenMemberName.setBounds(20, 120, 200, 20);
        inspectionAccessTokenTextField.setBounds(240, 120, 300, 40);


        inspectionRefreshTokenMemeberName.setBounds(20, 180, 200, 20);
        inspectionRefreshTokenTextField.setBounds(240, 180, 300, 40);

        tokenInspectionPanel.add(inspectionURILAbel);
        tokenInspectionPanel.add(inspectionAccessTokenMemberName);
        tokenInspectionPanel.add(inspectionRefreshTokenMemeberName);
        tokenInspectionPanel.add(inspectionSettingsSubmit);
        tokenInspectionPanel.add(inspectionURITextField);
        tokenInspectionPanel.add(inspectionAccessTokenTextField);
        tokenInspectionPanel.add(inspectionRefreshTokenTextField);
        tokenInspectionPanel.setBorder((Border) BorderFactory.createTitledBorder(inspectionConfigurationBorderTitle));
        tokenInspectionPanel.setVisible(boolInspectionValue);
        inspectionURITextField.setText(authToken.getWatchURI());

        inspectionAccessTokenTextField.setText(authToken.getAccessTokenLabel());
        inspectionRefreshTokenTextField.setText(authToken.getRefeshTokenLable());

        inspectionSettingsSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!inspectionURITextField.getText().isEmpty()){
                    authToken.setWatchURI(inspectionURITextField.getText());
                }
                if (!inspectionAccessTokenTextField.getText().isEmpty()){
                    authToken.setAccessTokenLabel(inspectionAccessTokenTextField.getText());
                }

                if (!inspectionRefreshTokenTextField.getText().isEmpty()){
                    authToken.setRefeshTokenLable(inspectionRefreshTokenTextField.getText());
                }




            }
        });

    }

    public String getURI(){
        return inspectionURITextField.getText();
    }

    public String getInspectionAccessTokenMemeberName(){
        return inspectionAccessTokenTextField.getText();
    }

    public String getInspectionRefreshTokenMemeberName(){
        return inspectionRefreshTokenTextField.getText();
    }



    public void tokenConfigurationAreaInit(){

        tokenConfigurationPanel = new JPanel();
        tokenConfigurationPanel.setLayout(null);
        tokenConfigurationPanel.setBounds(10,10,100,100);
        tokenConfigurationPanel.setLayout(new BoxLayout(tokenConfigurationPanel,BoxLayout.Y_AXIS));
        for (tokenConfigurationLabels label:tokenConfigurationLabels.values()
                ) {
            JCheckBox jL = new JCheckBox(label.toString());
            jL.setEnabled(false);
            if (label.toString().equalsIgnoreCase(tokenConfigurationLabels.Packet_Inspection.toString()))
            {
                jL.setEnabled(true);
                jL.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        setBoolInspectionValue(!boolInspectionValue);

                        accessTokenField.setEnabled(boolInspectionValue);
                        refTokenField.setEnabled(boolInspectionValue);
                        tokenInspectionPanel.setVisible(boolInspectionValue);
                        inspectionURITextField.setEnabled(boolInspectionValue);
                        inspectionAccessTokenTextField.setEnabled(boolInspectionValue);
                        inspectionRefreshTokenTextField.setEnabled(boolInspectionValue);

                        if(boolInspectionValue) {
                            callbacks.printOutput("[!] Inspection Enabled");

                        }
                        else {
                            callbacks.printOutput("[!] Inspection Disabled");

                        }

                    }
                });
            }


            tokenConfigurationPanel.add(jL);
        }
        tokenConfigurationPanel.setBorder((Border) BorderFactory.createTitledBorder(tokenConfigurationBorderTitle));
        tokenConfigurationPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


    }
    public void setTextFieldsValues(ArrayList<JTextField> textField, ArrayList<String> strValue) {

        if (textField.size() == strValue.size()) {
            for (JTextField fld : textField
            ) {
                fld.setText(strValue.get(textField.indexOf(fld)));
            }
        }

    }
    public void setTextFieldValue(String fieldName, String value){
        
        switch (fieldName){
            
            case "accessToken":
                accessTokenField.setText(value);
                break;
            case "refreshToken":
                refTokenField.setText(value);
                break;
            case "clientId":
                break;
            case "activeId":
                break;
            case "idToken":
                break;
            default :
                break;
                
        }
        
    }
    private void setAligment() {
        // Position
        accessTokenField.setEnabled(boolInspectionValue);
        refTokenField.setEnabled(boolInspectionValue);
        accessTokenLabel.setBounds(20, 50, 100, 20);
        accessTokenField.setBounds(140, 50, 300, 40);

        refTokenLabel.setBounds(20, 120, 100, 20);
        refTokenField.setBounds(140, 120, 300, 40);

        resetButton.setBounds(140, 180, 90, 20);
        updateButton.setBounds(240, 180, 120, 20);
        setValues.setBounds(370, 180, 120, 20);
    }

    public void configureComponents() {
        tokenPanel.setSize(1000, 1000);
        //  logsPanel.setSize(1000,1000);
        mainPanel.setSize(2000, 2000);
    }

    public void packtokenPanelComponents() {
        // Add components
        tokenPanel.add(refTokenField);
        tokenPanel.add(refTokenLabel);
        tokenPanel.add(accessTokenField);
        tokenPanel.add(accessTokenLabel);
        tokenPanel.add(updateButton);
        tokenPanel.add(resetButton);
        tokenPanel.add(setValues);

    }

    public void enableComponents() {
        //  logsPanel.setLayout(null);
        //  logsPanel.setVisible(true);
        tokenPanel.setLayout(null);
        tokenPanel.setVisible(true);
        mainPanel.setVisible(true);
    }


    public void addActions() {
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> str = new ArrayList<String>();
                str.add("");
                str.add("");
                ArrayList<JTextField> fields = new ArrayList<JTextField>();
                fields.add(accessTokenField);
                fields.add(refTokenField);
                setTextFieldsValues(fields, str);
            }
        });

        setValues.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> str = new ArrayList<String>();
                str.add(authToken.getAccessToken());
                str.add(authToken.getRefreshToken());
                ArrayList<JTextField> filds = new ArrayList<JTextField>();
                filds.add(accessTokenField);
                filds.add(refTokenField);
                setTextFieldsValues(filds, str);
            }
        });
    }


}
