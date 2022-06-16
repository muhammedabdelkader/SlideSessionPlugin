package com.slideSessionPlugin.gui;

import burp.IBurpExtenderCallbacks;
import burp.IHttpRequestResponse;
import com.slideSessionPlugin.data.loggerBook;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;

public class logsTable extends AbstractTableModel {
    public JTextArea request, response;
    private Table table;
    private ArrayList<loggerBook> logRow;
    private IHttpRequestResponse currentlyDisplayedItem;
    private IBurpExtenderCallbacks callbacks;

    public logsTable(IBurpExtenderCallbacks callbacks) {
        table = new Table(logsTable.this);
        logRow = new ArrayList<loggerBook>();
        this.callbacks = callbacks;


    }

    public Table showTable() {
        return table;
    }

    public ArrayList<loggerBook> getLogTable() {
        return logRow;
    }

    public void setReTextArea(JTextArea jTReq) {
        this.request = jTReq;
    }

    public void setResTextArea(JTextArea jTRes) {
        this.response = jTRes;
    }

    public void insert() {
        synchronized (logRow) {
            int row = logRow.size();
            fireTableRowsInserted(row, row);
        }
    }

    @Override
    public int getRowCount() {
        return logRow.size();
    }

    @Override
    public int getColumnCount() {
        return (new loggerBook()).getColumnCounts();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        loggerBook rows = logRow.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return callbacks.getToolName(rows.toolFlag);
            case 1:
                return rows.url.toString();
            case 2:
                return rows.token.toString();
            default:
                return -1;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return (new loggerBook()).getColumnName(columnIndex);
    }


    private class Table extends JTable {
        public Table(TableModel tableModel) {
            super(tableModel);
        }

        @Override
        public void changeSelection(int row, int col, boolean toggle, boolean extend) {
            loggerBook selectedLogRow = logRow.get(row);
            //TODO Add fireback events to calle class
            request.setText(callbacks.getHelpers().bytesToString(selectedLogRow.getRR().getRequest()));
            response.setText(callbacks.getHelpers().bytesToString(selectedLogRow.getRR().getResponse()));
            super.changeSelection(row, col, toggle, extend);
        }
    }


}
