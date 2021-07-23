package com.hit.client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


/**
 * 
 * @author ъош
 *
 *	A Client class which communicate with server.
 *
 */
public class CacheUnitClient {

	public CacheUnitClient() {}

	/**
	 *  send request to server and get a response.
	 * @param request
	 * @return
	 */
	public String send(String request) {
		String res = "failed";
		try {
			
			Socket socket = new Socket(InetAddress.getLocalHost(), 12345);
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			writer.println(request);
			writer.flush();
			
			res = read(socket);
			
			writer.close();
			socket.close();
			
		} catch (IOException e) {}
		return res;
	}

	//help method for reading all content arrived from server
	private String read(Socket socket) throws IOException {
		
		StringBuilder request = new StringBuilder();
		Scanner socketReader = new Scanner(new InputStreamReader(socket.getInputStream()));
		String line = "";
		while (true) {
			try {
				line = socketReader.nextLine();
				request.append("\n");
				request.append(line);

			} catch (Exception e) {
				socketReader.close();
				return request.toString();
			}
		}
	}

}
