/**
 * 
 */
package com.breckinloggins.cx.dictionary;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import com.breckinloggins.cx.Environment;

/**
 * @author bloggins
 * Base functionality for all readers
 */
public abstract class BaseReader implements IEntry, IReader {
	
	private PrintStream _writer;
	
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
	 * @see com.breckinloggins.cx.reader.IReader#setWriter(java.io.PrintStream)
	 */
	@Override
	public void setWriter(PrintStream writer) {
		_writer = writer;
	}
	
	/**
	 * Gets the writer used to write output
	 * @return The PrintStream for writing
	 */
	protected PrintStream getWriter()	{
		return _writer;
	}

	/* 
	 * @see com.breckinloggins.cx.reader.IReader#read(java.io.StringReader, com.breckinloggins.cx.Environment)
	 */
	@Override
	public IReader read(StringReader sr, Environment env) throws IOException {
		_writer.println("Warning: reader doesn't read anything");
		return null;
	}

}