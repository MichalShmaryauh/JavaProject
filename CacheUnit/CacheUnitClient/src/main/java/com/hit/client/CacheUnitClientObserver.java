package com.hit.client;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.hit.view.CacheUnitView;


/**
 * 
 * @author ъош
 *
 * This is observer class that performs events from UI
 * Get requests and handle them.
 *
 */
public class CacheUnitClientObserver implements PropertyChangeListener {

	private CacheUnitClient client;

	public CacheUnitClientObserver() {
		client = new CacheUnitClient();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		String command = evt.getPropertyName();
		CacheUnitView updateUI = (CacheUnitView) evt.getSource();
		String res = "";
		
		switch (command) {
		case "show statistics":
			res = handleShowStatistics();
			break;
		case "load a request":
			res = handleRequest((String) evt.getNewValue());
			break;
		}
		updateUI.updateUIData(res);
	}

	private String handleShowStatistics() {
		String request = "{ \"headers\":{\"action\":\"SHOW_STATISTICS\"},\"body\":[]}";
		return client.send(request);
	}

	private String handleRequest(String filePath) {

		try {
			return client.send(read(filePath));
		} catch (FileNotFoundException e) {
			return "";
		}

	}
	
	/**
	 *  Help method which read content of file and combine it to a string.
	 *  
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 */
	private String read(String filePath) throws FileNotFoundException {
		StringBuilder request = new StringBuilder();
		Scanner reader = new Scanner(new FileReader(filePath));
		String line = "";
		while (true){
			try{
				line =  reader.nextLine();
				request.append(line);
				
			}catch (NoSuchElementException  e){
				reader.close();
				return request.toString();
			}
		}
	}

}
