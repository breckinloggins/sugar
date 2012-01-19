package com.breckinloggins.cx;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
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
	 * Applies appearance properties to the given text area
	 * @param textArea The text area to theme
	 */
	public static void applyTheme(JTextArea textArea)	{
		Font f = new Font(Font.MONOSPACED, Font.PLAIN, 12);
		
		textArea.setBackground(Color.DARK_GRAY);
		textArea.setForeground(Color.WHITE);
		textArea.setCaretColor(Color.LIGHT_GRAY);
		textArea.setFont(f);
		textArea.setMargin(new Insets(3, 3, 3, 3));
		textArea.setTabSize(3);
	}
	
	/**
	 * Create the Nihilo Interpreter app menu bar
	 * @return The created menu bar
	 */
	public static JMenuBar createMenuBar()	{
		JMenuBar bar = new JMenuBar();
		JMenu run = new JMenu("Run");
		bar.add(run);
		
		JMenuItem runItem = new JMenuItem("Run");
		runItem.setEnabled(false);
		
		JMenuItem stopItem = new JMenuItem("Stop");
		stopItem.setEnabled(false);
		
		run.add(runItem);
		run.add(stopItem);
		
		return bar;
	}
	
	/**
	 * Sets the properties necessary to move the menus from the frame (Java default)
	 * to the top of the screen (Apple default)
	 * @param title The title of the main app menu
	 */
	public static void setAppleMenus(String title)	{
		try {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", title);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: " + e.getMessage());
		}
		catch(InstantiationException e) {
            System.out.println("InstantiationException: " + e.getMessage());
		}
		catch(IllegalAccessException e) {
            System.out.println("IllegalAccessException: " + e.getMessage());
		}
		catch(UnsupportedLookAndFeelException e) {
            System.out.println("UnsupportedLookAndFeelException: " + e.getMessage());
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// TODO:
		// - Rename all packages to sugar
		// - Add interpreter thread that can be controlled through run and stop menus
		// - Text in output should be in red if interpreter isn't running
		
		String title = "Sugar";
		
		setAppleMenus(title);
		
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setJMenuBar(createMenuBar());
		
		
		//
		// INPUT AREA
		//
		final JTextArea inputArea = new JTextArea();
		applyTheme(inputArea);
		JScrollPane inputScrollPane = new JScrollPane(inputArea);
		
		//
		// OUTPUT AREA
		//
		final JTextArea outputArea = new JTextArea();
		applyTheme(outputArea);
		outputArea.setEditable(false);
		JScrollPane outputScrollPane = new JScrollPane(outputArea);
		
		//
		// DEBUG AREA
		//
		final JTextArea debugArea = new JTextArea();
		applyTheme(debugArea);
		debugArea.setEditable(false);
		JScrollPane debugScrollPane = new JScrollPane(debugArea);
		
		JSplitPane ioSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				inputScrollPane, outputScrollPane);
		ioSplitPane.setDividerLocation(380);
		
		JSplitPane mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				ioSplitPane, debugScrollPane);
		mainSplitPane.setDividerLocation(400);
		
		frame.getContentPane().add(mainSplitPane);
		
		frame.setSize(1050, 600);
		frame.setVisible(true);
		
		System.setOut(new PrintStream(createOutputStreamForTextArea(outputArea)));
		System.setErr(new PrintStream(createOutputStreamForTextArea(debugArea)));
		
		// Set System.in to a piped stream that we will control with our
		// text area
		PipedInputStream piStream = new PipedInputStream();
		final PipedOutputStream poStream = new PipedOutputStream();
		try {
			piStream.connect(poStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.setIn(piStream);
		
		final Interpreter interp = new Interpreter();
		
		KeyListener keyListener = new KeyListener() {

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
				
				try {
					poStream.write(k);
					poStream.flush();
				} catch (IOException e) {
					e.printStackTrace();
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
		
		Thread t = new Thread(interp);
		t.start();
	}
}
