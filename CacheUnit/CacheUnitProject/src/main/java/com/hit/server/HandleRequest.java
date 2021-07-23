package com.hit.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.Gson;
import com.hit.dm.DataModel;
import com.hit.services.CacheUnitController;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author ъош
 *
 *         A class which get a request and handle the suitable action.
 *
 * @param <T>
 */

public class HandleRequest<T> implements Runnable {

	private Socket socket;
	private CacheUnitController<T> controller;

	public HandleRequest(Socket s, CacheUnitController<T> controller) {
		this.socket = s;
		this.controller = controller;
	}

	public void run() {
		Boolean success = false;
		String res = "";

		try {

			//read request
			Scanner reader = new Scanner(new InputStreamReader(socket.getInputStream()));
			Type ref = new TypeToken<Request<DataModel<T>[]>>() {
			}.getType();
			Request<DataModel<T>[]> request = new Gson().fromJson(reader.nextLine(), ref);
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

			String action = request.getHeaders().get("action");
			switch (action) {

			case "DELETE":
				success = controller.delete(request.getBody());
				break;

			case "UPDATE":
				success = controller.update(request.getBody());
				break;

			case "GET":
				success = controller.get(request.getBody()) != null;
				break;

			case "UPDATE_ALL":
				success = controller.updateDiskByCache();
				break;

			case "SHOW_STATISTICS":
				res = controller.showStatistics();
				break;

			default:
				System.out.println("HandleRequest: request headers not supported");
				break;
			}
			if(res.equals("")){
				if(success){
					res = "Succeeded";
				}
				else {
					res = "Failed";
				}
			}
			writer.println(res);
			writer.flush();
			writer.close();
			reader.close();
		} catch (IOException e) {
			System.out.println("HandleReqest: Failed to get request input stream");
		}

	}

}
