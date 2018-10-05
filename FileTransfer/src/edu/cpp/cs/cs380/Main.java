package edu.cpp.cs.cs380;

import java.awt.EventQueue;

public class Main {
		
//	//Invokes GUI and starts chain of events
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmFileTransfer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
