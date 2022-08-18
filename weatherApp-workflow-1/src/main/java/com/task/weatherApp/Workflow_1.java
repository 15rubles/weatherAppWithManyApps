package com.task.weatherApp;


import com.task.weatherApp.server.Server;

import java.io.IOException;

public class Workflow_1 {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.runServer();
    }
}
