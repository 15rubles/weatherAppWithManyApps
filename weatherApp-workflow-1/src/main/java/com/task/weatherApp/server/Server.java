package com.task.weatherApp.server;

import com.task.weatherApp.cadence.workflow.WorkflowWorkerGetWeather;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final WorkflowWorkerGetWeather workflow = new WorkflowWorkerGetWeather();

    public void runServer() throws IOException {
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        ServerSocket serverSocket = null;

        serverSocket = new ServerSocket(1234);

        while(true){
            try{
                socket = serverSocket.accept();
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);


                String msgFromClient = bufferedReader.readLine();
                String msgToClient = null;
                msgToClient = String.valueOf(workflow.workflowWorkerLaunch(msgFromClient));

                bufferedWriter.write(msgToClient);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                socket.close();
                inputStreamReader.close();
                outputStreamWriter.close();
                bufferedReader.close();
                bufferedWriter.close();


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
