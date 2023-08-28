package de.umr.ds.task2;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TextClient {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
        //setup socket
        String server = "localhost";
        int port = 32823;
        //create Socket
        Socket socket = null;
        try {
            socket = new Socket(server, port);
			System.err.println("Connected to server " + socket.getInetAddress() + " on port " + socket.getPort());
        } catch (IOException e) {
            System.err.println("Server not found");
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
        PrintWriter writer = new PrintWriter(os);

        InputStream is = socket.getInputStream();
        InputStreamReader bis = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(bis);
        while (true) {
			//write message
            String message = input.nextLine();
            writer.println(message);
            writer.flush();
			if (message.equals("close")) {
				//close connection
				System.out.println("Closing connection");
                socket.close();
                break;
			}
            //receive response from server

            String response;
            try {
                response = reader.readLine();
                System.out.println("Server: " + response);
            } catch (EOFException e) {
                System.err.println("Server closed connection");
                break;
            }
        }
    }
}