package com.hit.server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.hit.services.CacheUnitController;

/**
 * 
 * @author ъош
 *
 *A class which represent the server actions.
 *the server is running on a new thread which is created when getting "START" command from the CLI.
 *the server listen to requests from clients and handle them by handle request.
 *
 */

public class Server implements PropertyChangeListener, Runnable {

	private ServerSocket serverSocket;
	private CacheUnitController<String> cacheUnitController;
	private Executor executor;
	private Boolean isConnected;
	static private final int port = 12345;

	public Server() {
		isConnected = false;
	}

	private void initialServerAttributes() {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Starting server.......");
			executor = Executors.newCachedThreadPool();
			cacheUnitController = new CacheUnitController<String>();
		} catch (IOException e) {
			System.out.println("Server: Failed to open server socket");
			e.printStackTrace();
		}
	}

	private void shutDown() {
		try {
			
			//create shutdown request
			String shutDownRequest = "{\"headers\" :{\"action\" : \"UPDATE_ALL\"}, \"body\":[]";

			Socket shutDownSocket = new Socket(InetAddress.getLocalHost(), port);
			
			new ObjectOutputStream(shutDownSocket.getOutputStream()).writeObject(shutDownRequest);
			HandleRequest<String> HandleShutdownRequest = new HandleRequest<String>(shutDownSocket,
					cacheUnitController);
			
			executor.execute(HandleShutdownRequest);
			((ExecutorService) executor).shutdown();
			
			serverSocket.close();
			System.out.println("Shutdown server...");
		} catch (IOException e) {
			System.out.println("Server: Failed to update disk from cache before shutDown or close socket");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		initialServerAttributes();
		while (isConnected) {

			try {
				Socket newClient = serverSocket.accept();
				HandleRequest<String> RequesterHandler = new HandleRequest<String>(newClient, cacheUnitController);
				executor.execute(RequesterHandler);

			} catch (IOException e) {}
		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		String command = (String) evt.getNewValue();

		switch (command) {

		case "START":
			if (isConnected == false) {
				isConnected = true;
				new Thread(this).start();
			} else
				System.out.println("Server is already up\n");
			break;

		case "SHUTDOWN":
			if (isConnected == true) {
				isConnected = false;
				shutDown();
			} else
				System.out.println("Server is already down\n");
			break;

		default:
			System.out.println("Server: unsupported command");
			break;
		}

	}

}
