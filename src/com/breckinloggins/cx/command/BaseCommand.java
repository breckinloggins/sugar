/**
 * 
 */
package com.breckinloggins.cx.command;

import java.io.PrintStream;

import com.breckinloggins.cx.Environment;

/**
 * @author bloggins
 *
 */
public abstract class BaseCommand implements ICommand {

	private PrintStream _writer;
	
	/*
	 * @see com.breckinloggins.cx.command.ICommand#getName()
	 */
	@Override
	public String getName() {
		return this.getClass().getName();
	}

	/*
	 * @see com.breckinloggins.cx.command.ICommand#getDescription()
	 */
	@Override
	public String getDescription() {
		return "(command)";
	}

	/*
	 * @see com.breckinloggins.cx.command.ICommand#execute(com.breckinloggins.cx.Environment)
	 */
	@Override
	public void execute(Environment env) {
		_writer.println("WARNING: The command \"" + getName() + "\" has nothing to do");
	}

	/*
	 * @see com.breckinloggins.cx.command.ICommand#setWriter(java.io.PrintStream)
	 */
	@Override
	public void setWriter(PrintStream writer) {
		_writer = writer;
	}

	/**
	 * Gets the writer to which this command can write string output
	 * @return The PrintStream writer
	 */
	protected PrintStream getWriter()	{
		return _writer;
	}
}
