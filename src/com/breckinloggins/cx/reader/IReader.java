package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import com.breckinloggins.cx.Environment;

public interface IReader {
	/**
	 * Gets the human-readable description of this reader
	 * @return the description
	 */
	String getDescription();
	
	/**
	 * Sets the print-stream (System.out-like) writer to which all text output from this
	 * reader will be sent
	 * @param writer The writer to use
	 */
	void setWriter(PrintStream writer);
	
	/**
	 * Reads a string from the given string reader, interprets it, and returns the next reader
	 * @param sr The StringReader from which to read input
	 * @param env The current scope environment
	 * @return The next reader, which may be null, the same reader, or a different reader
	 * @throws IOException
	 */
	IReader read(StringReader sr, Environment env) throws IOException;
}
