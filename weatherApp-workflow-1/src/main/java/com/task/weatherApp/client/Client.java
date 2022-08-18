package com.task.weatherApp.client;

import java.io.*;
import java.net.Socket;


public class Client {
    public static String postWeather(String city, Double temperature){
        Socket socket = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        String weather = null;
        try {

            socket = new Socket("localhost", 5678);

            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());


            bufferedWriter = new BufferedWriter(outputStreamWriter);


            bufferedWriter.write(city);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            bufferedWriter.write(String.valueOf(temperature));
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (socket != null) {
                    socket.close();
                }
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        return weather;
    }
}
