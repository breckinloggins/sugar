package org.sugarlang;

public class InterpreterThread {
	private Interpreter _interpreter;
	private Thread _thread;
	
	/**
	 * Creates a new interpreter thread
	 */
	public InterpreterThread()	{
		_interpreter = new Interpreter();
		_thread = new Thread(_interpreter);
	}
	
	/**
	 * Gets the interpreter that this InterpreterThread managers
	 * @return The interpreter
	 */
	public Interpreter getInterpreter()	{
		return _interpreter;
	}
	
	/**
	 * Starts the interpeter thread
	 */
	public void start()	{
		_thread.start();
	}
	
	/**
	 * Signals to the thread that it should stop running and waits for
	 * it to stop
	 */
	public void stop()	{
		_interpreter.stop();
		try {
			_thread.join(1000);
			if (_thread.isAlive())	{
				_thread.interrupt();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isRunning()	{
		return _thread.isAlive();
	}
}
