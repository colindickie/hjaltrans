package com.hjaltrans.test3;

public class Class_RowItem {
    private String port;
    private int portID;
    private int time;
     
    public Class_RowItem(String port, int portID, int time) {
        this.port = port;
        this.portID = portID;
        this.time = time;
    }


    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public int getPortID() {
        return portID;
    }
    public void setPortID(int portID) {
        this.portID = portID;
    }
    @Override
    public String toString() {
        return port + "\n" + time;
    }  
}
