package com.breckinloggins.cx.dictionary;

import java.io.IOException;
import java.io.StringReader;

import com.breckinloggins.cx.Environment;

/**
 * Defines an interface for objects that read zero or more characters from input, perform some action, 
 * then return a new reader.
 * 
 * Note to implementors: readers should be stateless.  All state should be stored in the environment or other
 * context objects.
 * @author bloggins
 *
 */
public interface IReader {
	/**
	 * Reads a string from the given string reader, interprets it, and returns the next reader
	 * @param sr The StringReader from which to read input
	 * @param env The current scope environment
	 * @return The next reader, which may be null, the same reader, or a different reader
	 * @throws IOException
	 */
	IReader read(StringReader sr, Environment env) throws IOException;
}
