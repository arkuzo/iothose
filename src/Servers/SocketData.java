package Servers;

import core.Data;

public class SocketData implements Data {
    private String response;

    public SocketData(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
