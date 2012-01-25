package org.sugarlang;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class Main {

	private static InterpreterThread _interpreterThread;
	private static JMenuItem _runMenuItem;
	private static JMenuItem _stopMenuItem;
	
	// Returns the contents of the file in a byte array.
	public static byte[] getBytesFromFile(java.io.File file) throws IOException {
	    InputStream is = new java.io.FileInputStream(file);

	    // Get the size of the file
	    long length = file.length();

	    // You cannot create an array using a long type.
	    // It needs to be an int type.
	    // Before converting to an int type, check
	    // to ensure that file is not larger than Integer.MAX_VALUE.
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    }

	    // Create the byte array to hold the data
	    byte[] bytes = new byte[(int)length];

	    // Read in the bytes
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }

	    // Ensure all the bytes have been read in
	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }

	    // Close the input stream and return bytes
	    is.close();
	    return bytes;
	}
	
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
	 * @param interpreterThread the thread that items in the run menu will manipulate
	 * @return The created menu bar
	 */
	public static JMenuBar createMenuBar()	{
		JMenuBar bar = new JMenuBar();
		JMenu run = new JMenu("Run");
		bar.add(run);
		
		JMenuItem runItem = new JMenuItem("Run");
		JMenuItem stopItem = new JMenuItem("Stop");
		
		runItem.setEnabled(false);
		stopItem.setEnabled(false);
		
		run.add(runItem);
		run.add(stopItem);
		
		_runMenuItem = runItem;
		_stopMenuItem = stopItem;
		
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
		// - Fix reconnection of input stream after thread terminated
		// - Text in output should be in red if interpreter isn't running
	
		String title = "Sugar";
		
		String firstArg = "";
		if (args.length > 0)	{
			firstArg = args[0];
		}
		
		final String preludeFile = firstArg;
		
		setAppleMenus(title);
		
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		final PipedInputStream piStream = new PipedInputStream();
		final PipedOutputStream poStream = new PipedOutputStream();
		try {
			piStream.connect(poStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.setIn(piStream);
		
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
		
		// Has to be done after the input streams are redirected or the new thread won't see the 
		// piped stream
		_interpreterThread = new InterpreterThread();
		frame.setJMenuBar(createMenuBar());
		_runMenuItem.setEnabled(true);
		_runMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_runMenuItem.setEnabled(false);
				_stopMenuItem.setEnabled(true);
				_interpreterThread.start();
			}
			
		});
		
		_stopMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_stopMenuItem.setEnabled(false);
				_interpreterThread.stop();
				_runMenuItem.setEnabled(true);
			}
		});
		
		_interpreterThread.getInterpreter().setListener(new Interpreter.Listener() {
			
			@Override
			public void interpreterStarted(Interpreter e) {
				if (preludeFile != "")	{
					System.err.println("Executing prelude: " + preludeFile);
					SwingUtilities.invokeLater(new Runnable()	{

						@Override
						public void run() {
							try {
								byte[] b = getBytesFromFile(new java.io.File(preludeFile));						
								poStream.write(b);
							} catch (IOException ex) {
								ex.printStackTrace(System.err);
							}		
						}
					});
				}
			}
		});
		
		
		_runMenuItem.doClick();
	}
}
