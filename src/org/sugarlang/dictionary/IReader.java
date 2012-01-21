package org.sugarlang.dictionary;

import java.io.IOException;

import org.sugarlang.Environment;


/**
 * Defines an interface for objects that read zero or more characters from input, perform some action, 
 * then return a new reader.
 * 
 * Note to implementors: reader classes should be stateless.  All state should be stored in the environment or other
 * context objects.
 * @author bloggins
 * 
 * TODO: Get rid of (almost) all of these.  We should be able to implement readers in Sugar
 */
public interface IReader {
	/**
	 * Reads a string from the given string reader and interprets it
	 * @param env The current scope environment
	 */
	void read(Environment env) throws IOException;
}
