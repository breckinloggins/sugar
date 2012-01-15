package com.breckinloggins.cx;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Main {

	/**
	 * Creates an output stream that writes to a text area instead of to the console
	 * @param textArea The JTextArea to which to write
	 * @return The new OutputStream, which can be used to construct a PrintStream
	 */
	public static OutputStream createOutputStreamForTextArea(final JTextArea textArea)	{
		OutputStream out = new OutputStream() {  
		    private void updateTextArea(final String str)	{
		    	SwingUtilities.invokeLater(new Runnable() {  
		    		public void run() {  
		    			textArea.append(str);  
		    		}  
		    	});  
		    }
			
			@Override  
		    public void write(int b) throws IOException {  
		      updateTextArea(String.valueOf((char) b));  
		    }  
		  
		    @Override  
		    public void write(byte[] b, int off, int len) throws IOException {  
		      updateTextArea(new String(b, off, len));  
		    }  
		  
		    @Override  
		    public void write(byte[] b) throws IOException {  
		      write(b, 0, b.length);  
		    }  
		  };  
		  
		  return out;
	}
	
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
		// - Change text area backgrounds to dark grey
		// - Change fonts to mono space
		
		frame.getContentPane().add(splitPane);
		
		frame.setSize(600, 400);
		frame.setVisible(true);
		
		PrintStream out = new PrintStream(createOutputStreamForTextArea(outputArea));
		
		final Interpreter interp = new Interpreter(out);
		
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
