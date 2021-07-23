package com.hit.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * 
 * @author ъош
 *
 *         CLI get command from user and update all observers.
 *
 */

public class CLI implements Runnable {

	private PropertyChangeSupport supportChanges = new PropertyChangeSupport(this);
	private BufferedReader in;
	// private OutputStream out;
	private String[] availableCommand = { "START", "SHUTDOWN" };

	public CLI(InputStream in, OutputStream out) {
		this.in = new BufferedReader(new InputStreamReader(in));
		// this.out = out;
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		supportChanges.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		supportChanges.removePropertyChangeListener(pcl);
	}

	public void write(String string) {
		System.out.println(string);
	}

	@Override
	public void run() {
		write("Please enter a command");
		String command;
		try {
			while ((command = in.readLine()) != null) {
				command = command.toUpperCase();
				if (isInAvailableCommand(command)) {
					supportChanges.firePropertyChange("CLI_Command", null, command);
				} else {
					write("Not a valid command");
				}
			}
		} catch (Exception e) {
		}

	}

	private boolean isInAvailableCommand(String command) {
		for (String string : availableCommand) {
			if (string.equals(command)) {
				return true;
			}
		}
		return false;
	}

}
