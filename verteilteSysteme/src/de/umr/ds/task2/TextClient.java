package de.umr.ds.task2;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TextClient {

	public static <BufferedReader> void main(String[] args) throws IOException, ClassNotFoundException {
        //setup socket
        String server = "dsgw.mathematik.uni-marburg.de";
        int port = 32823;
        //create Socket
        Socket socket = null;
        try {
            socket = new Socket(server, port);
			System.out.println("Connected to server " + socket.getInetAddress() + " on port " + socket.getPort());
        } catch (IOException e) {
            System.out.println("Server not found");
            System.exit(0);
        }
		//print server info
		System.out.println(socket.isConnected());
		System.out.println(socket.getLocalPort());
        //take inputs from console
        Scanner input = new Scanner(System.in);
		System.out.println("Enter message to send to server: ");

        //send inputs to server
        OutputStream os = socket.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        while (true) {
			//write message
            String message = input.nextLine();
            bos.write(message.getBytes());
            bos.flush();
			if (message.equals("close")) {
				//close connection
				System.out.println("Closing connection");
                socket.close();
                break;
			}
            //receive response from server
            InputStream is = socket.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            String response;
            try {
                response = String.valueOf(bis.read());
                System.out.println("Server: " + response);
            } catch (EOFException e) {
                System.out.println("Server closed connection");
                break;
            }
        }
    }
}