/**
 * 
 */
package com.breckinloggins.cx.command;

import java.io.PrintStream;

import com.breckinloggins.cx.Environment;

/**
 * @author bloggins
 * Represents a command that can be performed.  Commands always represent side-effects and
 * are purely stack based.  That is, commands don't have named arguments, although command READERS
 * often implement this semantic.  Stack arguments are always on the given environment's stack
 */
public interface ICommand {
	
	/**
	 * Gets the human-readable name of this command (note that the current alias may be different)
	 * @return The name
	 */
	String getName();
	
	/**
	 * Gets the human-readable description of this command
	 * @return The description
	 */
	String getDescription();
	
	/**
	 * Executes the comment within the current environment
	 * @param env The environment in which to execute the command.  The environment's stack
	 * may be affected by this command
	 */
	void execute(Environment env);

	/**
	 * Sets the PrintStream by which this command can write to string output
	 * @param writer The PrintStream writer to use
	 */
	void setWriter(PrintStream writer);
	
}
