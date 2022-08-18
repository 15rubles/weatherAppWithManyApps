package com.task.weatherApp.server;

import com.task.weatherApp.cadence.workflow.WorkflowWorkerPostWeather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class LocalServer {


    private final WorkflowWorkerPostWeather workflowWorkerPostWeather;
    @Autowired
    public LocalServer(WorkflowWorkerPostWeather workflowWorkerPostWeather) throws IOException {
        this.workflowWorkerPostWeather = workflowWorkerPostWeather;
        runServer();
    }


    public void runServer() throws IOException {
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        ServerSocket serverSocket = null;

        serverSocket = new ServerSocket(5678);

        while(true){
            try{
                socket = serverSocket.accept();
                inputStreamReader = new InputStreamReader(socket.getInputStream());

                bufferedReader = new BufferedReader(inputStreamReader);


                String msgFromClient_City = bufferedReader.readLine();
                String msgFromClient_temperature = bufferedReader.readLine();

                workflowWorkerPostWeather.workflowWorkerLaunch(msgFromClient_City, Double.valueOf(msgFromClient_temperature));

                socket.close();
                inputStreamReader.close();
                bufferedReader.close();



            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
