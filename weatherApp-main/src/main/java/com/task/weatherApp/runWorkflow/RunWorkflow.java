package com.task.weatherApp.runWorkflow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunWorkflow {
    public static void run(String args[]) {
        Process theProcess = null;
        BufferedReader inStream = null;

        // call the Hello class
        try {
            theProcess = Runtime.getRuntime().exec("java QIBMHello");
        } catch (IOException e) {
            System.err.println("Error on exec() method");
        }

        // read from the called program's standard output stream
        try {
            inStream = new BufferedReader(
                    new InputStreamReader(theProcess.getInputStream()));
            System.out.println(inStream.readLine());
        } catch (IOException e) {
            System.err.println("Error on inStream.readLine()");
            e.printStackTrace();
        }
    }

}
