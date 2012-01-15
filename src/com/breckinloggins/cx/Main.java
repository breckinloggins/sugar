package com.breckinloggins.cx;

import java.io.StringReader;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Interpreter Window");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextArea inputArea = new JTextArea();
		JScrollPane inputScrollPane = new JScrollPane(inputArea);
		
		JTextArea outputArea = new JTextArea();
		JScrollPane outputScrollPane = new JScrollPane(outputArea);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				inputScrollPane, outputScrollPane);
		splitPane.setDividerLocation(380);
		
		// TODO:
		// 1. Have the interpreter and environment take a configurable Out stream
		// 2. Hook out stream to output area
		// 3. Change fonts to mono space
		
		frame.getContentPane().add(splitPane);
		
		frame.setSize(600, 400);
		frame.setVisible(true);
		
		final Interpreter interp = new Interpreter(System.out);
		
		KeyListener keyListener = new KeyListener() {
			// TODO: interpreter should read one character at a time
			private String _tmpCurrentLine = "";
			
			@Override
			public void keyPressed(KeyEvent arg0) {}

			@Override
			public void keyReleased(KeyEvent arg0) {}

			@Override
			public void keyTyped(KeyEvent arg0) {
				char k = arg0.getKeyChar();
				if (k == '\n')	{
					StringReader sr = new StringReader(_tmpCurrentLine);
					interp.TEMP_read(sr);
					
					_tmpCurrentLine = "";
				} else {
					_tmpCurrentLine += k;
				}	
			}
			
		};
		inputArea.addKeyListener(keyListener);		
	}

}
