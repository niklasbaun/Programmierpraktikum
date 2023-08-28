package de.umr.ds.task2;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class EchoServer {

	static final int PORT = 32823;
	public static void main(String[] args) {
		//create server socket
		ServerSocket serverSocket = null;

		//setup socket
		try {
			serverSocket = new ServerSocket(PORT);
			System.err.println("Started server on port " + PORT);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + PORT);
			System.exit(-1);
		}
		//setup thread pool
		Executors.newFixedThreadPool(1);

		//accept connections
		while (true) {
			try {
				Socket clientSocket = serverSocket.accept();
				System.err.println("Accepted connection from client " + clientSocket.getInetAddress() + " on port " + clientSocket.getPort());
				//create new thread for each client
				new Thread(new EchoServerThread(clientSocket)).start();
			} catch (IOException e) {
				System.err.println("Failed to accept connection");
				System.exit(-1);
			}
		}
	}
}
