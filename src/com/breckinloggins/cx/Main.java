package com.breckinloggins.cx;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

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
		
		Font f = new Font(Font.MONOSPACED, Font.PLAIN, 12);
		
		final JTextArea inputArea = new JTextArea();
		inputArea.setBackground(Color.DARK_GRAY);
		inputArea.setForeground(Color.WHITE);
		inputArea.setCaretColor(Color.LIGHT_GRAY);
		inputArea.setFont(f);
		inputArea.setMargin(new Insets(3, 3, 3, 3));
		inputArea.setTabSize(3);
		JScrollPane inputScrollPane = new JScrollPane(inputArea);
		
		JTextArea outputArea = new JTextArea();
		outputArea.setBackground(Color.DARK_GRAY);
		outputArea.setForeground(Color.WHITE);
		outputArea.setEditable(false);
		outputArea.setFont(f);
		outputArea.setMargin(new Insets(3, 3, 3, 3));
		outputArea.setTabSize(3);
		JScrollPane outputScrollPane = new JScrollPane(outputArea);
		
		// TODO: Need a third text area for the debug output as opposed to the command/program output
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				inputScrollPane, outputScrollPane);
		splitPane.setDividerLocation(380);
		
		frame.getContentPane().add(splitPane);
		
		frame.setSize(600, 500);
		frame.setVisible(true);
		
		PrintStream out = new PrintStream(createOutputStreamForTextArea(outputArea));
		
		final Interpreter interp = new Interpreter(out);
		
		KeyListener keyListener = new KeyListener() {
			// TODO: 
			// - Interpreter should read one character at a time
			// - A critical piece will have to be deletion, replacing logic
			// What does it mean for the user to erase or replace content?  I think we'll have to have
			// the notion of undoing stuff after the caret.  We'll also need the notion of an eval point
			// so that they can backspace in the middle of an eval expression, we undo that expression, and
			// wait for them to complete it again.
			private String _tmpCurrentLine = "";
			
			public boolean isIgnored(int keyCode)	{
				return (keyCode == KeyEvent.VK_BACK_SPACE ||
						keyCode == KeyEvent.VK_DELETE ||
						keyCode == KeyEvent.VK_LEFT ||
						keyCode == KeyEvent.VK_UP);
						
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (isIgnored(arg0.getKeyCode()))	{
					arg0.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (isIgnored(arg0.getKeyCode()))	{
					arg0.consume();
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				char k = arg0.getKeyChar();
				if (k == '\b')	{
					return;
				}
				
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
		
		// Attempt to keep the user from using the mouse to move the caret to a previous
		// spot in the code.  We can remove this as soon as we support undo
		final CaretListener caretListener = new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent arg0) {
				if (arg0.getDot() < inputArea.getText().length())	{
					inputArea.setCaretPosition(inputArea.getText().length());
				}
				
			}
			
		};
		inputArea.addCaretListener(caretListener);
	}

}
