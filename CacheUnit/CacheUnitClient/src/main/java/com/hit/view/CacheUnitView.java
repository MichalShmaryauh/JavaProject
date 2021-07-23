package com.hit.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.filechooser.*;

public class CacheUnitView {

	public class CacheUnitPanel extends JPanel implements ActionListener {

		private static final long serialVersionUID = 1L;

		private JTextArea textArea;

		public CacheUnitPanel() {

			setLayout(new BorderLayout());
			JButton loadRequest = new JButton("load a request") {
				private static final long serialVersionUID = 1L;
				{
					setSize(150, 75);
					setMaximumSize(getSize());
				}
			};
			loadRequest.addActionListener(this);
			add(loadRequest, BorderLayout.LINE_START);

			JButton showStatistics = new JButton("show statistics") {
				private static final long serialVersionUID = 1L;
				{
					setSize(150, 75);
					setMaximumSize(getSize());
				}
			};
			showStatistics.addActionListener(this);
			add(showStatistics, BorderLayout.LINE_END);

			JLabel title = new JLabel("MMU Project");
			title.setForeground(new Color(100, 100, 100));
			title.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 110));
			add(title, BorderLayout.CENTER);

			Border border = BorderFactory.createLineBorder(Color.BLACK);
			textArea = new JTextArea(30, 80);
			textArea.setBorder(border);
			textArea.setForeground(new Color(0, 128, 128));
			textArea.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 20));
			add(textArea, BorderLayout.PAGE_END);
		}

		public void actionPerformed(ActionEvent event) {
			// create an object of JFileChooser class
			if (event.getActionCommand().equals("load a request")) {
				JFileChooser j = new JFileChooser("src/main/resources");

				// set a title for the dialog
				j.setDialogTitle("Select a .json file");

				// restrict the user to select files of all types
				j.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .json files", "json");
				j.addChoosableFileFilter(restrict);

				int r = j.showOpenDialog(null);

				// if the user selects a file
				if (r == JFileChooser.APPROVE_OPTION) {
					// set the label to the path of the selected file
					PropertyChangeEvent evt = new PropertyChangeEvent(CacheUnitView.this, "load a request", null,
							j.getSelectedFile().getPath());
					changeSupport.firePropertyChange(evt);
				}

			} else if (event.getActionCommand().equals("show statistics")) {
				PropertyChangeEvent evt = new PropertyChangeEvent(CacheUnitView.this, "show statistics", null, null);
				changeSupport.firePropertyChange(evt);
			}
		}

		public void setTextAreaContent(String s) {
			textArea.setText(s);
		}
	}

	private JFrame f;
	private PropertyChangeSupport changeSupport;
	private CacheUnitPanel panel;

	public CacheUnitView() {
		// frame to contains GUI elements
		f = new JFrame("MMU Project");
		changeSupport = new PropertyChangeSupport(this);
		panel = new CacheUnitPanel();

	}

	public void start() {
		f.setBounds(1000, 1000, 1000, 1000);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(panel);
		f.setVisible(true);
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		changeSupport.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		changeSupport.removePropertyChangeListener(pcl);
	}

	public <T> void updateUIData(T t) {
		panel.setTextAreaContent((String) t);
	}
}

