package org.sugarlang.dictionary;

import java.io.IOException;

import org.sugarlang.Environment;


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
	 * @param env The current scope environment
	 */
	void read(Environment env) throws IOException;
}
