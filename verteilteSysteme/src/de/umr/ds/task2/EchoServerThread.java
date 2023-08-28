package de.umr.ds.task2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;

public class EchoServerThread extends Thread {
    protected Socket clientSocket;

    public EchoServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * This method is called when the thread runs
     */
    public void run() {
        //setup for input and output
        InputStream inputS = null;
        BufferedReader reader = null;
        DataOutputStream writer = null;

        try {
            //get input and output streams
            inputS = clientSocket.getInputStream();
            reader = new BufferedReader(new java.io.InputStreamReader(inputS));
            writer = new DataOutputStream(clientSocket.getOutputStream());
        } catch (Exception e) {
            System.err.println("Failed to get streams");
            System.exit(-1);
        }
        //setup to read from input
        String line;
        while (true){
            try {
                //read from input
                line = reader.readLine();
                //write to output
                writer.writeBytes(line + "\n");
                //flush output
                writer.flush();
                //close connection if a client sends "close"
                if (line.equals("close")) {
                    System.out.println("Closing connection");
                    clientSocket.close();
                    break;
                }
            } catch (Exception e) {
                System.err.println("Failed to read from input");
                System.exit(-1);
            }
        }

    }
}
