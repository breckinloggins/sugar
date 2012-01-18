/**
 * 
 */
package com.breckinloggins.cx.dictionary;

import java.io.IOException;

import com.breckinloggins.cx.Environment;

/**
 * @author bloggins
 * Base functionality for all readers
 */
public abstract class BaseReader implements IEntry, IReader {
	
	@Override
	/*
	 * @see com.breckinloggins.cx.IEntry#getName()
	 */
	public String getName()	{
		return this.getClass().getName();
	}
	
	/*
	 * @see com.breckinloggins.cx.IEntry#getDescription()
	 */
	@Override
	public String getDescription()	{
		return "(reader)";
	}
	
	/* 
	 * @see com.breckinloggins.cx.reader.IReader#read(com.breckinloggins.cx.Environment)
	 */
	@Override
	public void read(Environment env) throws IOException {
		System.err.println("Warning: reader doesn't read anything");
	}

	/**
	 * Reads a character from standard input and pushes it on the stack.  If the top of the 
	 * stack already has an integer, assumes it is a character and leaves it there.
	 * @param env The environment to run the commands
	 * @return The character, -1 if EOF, -2 if Error
	 */
	protected void readChar(Environment env)	{
		if (!env.isStackEmpty() && (env.peek() instanceof Integer))	{
			// We have an integer on top of the stack.  Assume it's a character and leave it
			return;
		}
		
		env.pushCommand("getchar");
		env.evaluateStack();
	}
	
	/**
	 * If there is a whitespace character on the stack, discards it
	 * @param env The environment to examine
	 */
	protected void discardWhitespace(Environment env)	{
		if (!env.isStackEmpty() && (env.peek() instanceof Integer))	{
			if (Character.isWhitespace((Integer)env.peek()))	{
				env.pop();
			}
		}
	}
}
