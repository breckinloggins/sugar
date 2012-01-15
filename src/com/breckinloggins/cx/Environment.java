package com.breckinloggins.cx;

import java.io.PrintStream;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

import com.breckinloggins.cx.command.ICommand;
import com.breckinloggins.cx.reader.IReader;

public class Environment {
	
	private HashMap<String, String> _readers;
	private HashMap<String, String> _commands;
	private PrintStream _writer;
	private Stack<String> _stack;
	
	public Environment(PrintStream writer)	{
		_readers = new HashMap<String, String>();
		_commands = new HashMap<String, String>();
		_writer = writer;
		_stack = new Stack<String>();
	}
	
	/**
	 * Returns the Java class name of the IReader by the given alias
	 * @param alias A registered alias for a reader
	 * @return The class name of the reader, or NULL
	 */
	public String getReaderClassName(String alias)	{
		return _readers.get(alias);
	}
	
	/**
	 * Sets the alias for the given reader in this environment
	 * @param alias The alias by which to refer to the reader
	 * @param className The name of the class for the reader
	 */
	public void setReaderAlias(String alias, String className)	{
		_readers.put(alias, className);
	}
	
	/**
	 * Gets a set of all registered reader aliases
	 * @return The set of aliases
	 */
	public Set<String> getReaderAliases()	{
		return _readers.keySet();
	}
	
	/**
	 * Creates a reader object by the given alias
	 * @param alias The alias by which the reader is referred
	 * @return The reader object, or NULL if no reader was found by that alias
	 */
	public IReader createReader(String alias)	{
		String className = this.getReaderClassName(alias);
		if (className == null || className.isEmpty())	{
			return null;
		}
		
		try {
			Class<?> theClass = Class.forName(className);
			IReader reader = (IReader)theClass.newInstance();
			reader.setWriter(_writer);
			return reader;
			
		} catch (ClassNotFoundException e) {
			// The class name was registered, but doesn't really exist
			_writer.println("The reader alias \"" + alias + "\" does not have a backing class");
			return null;
		} catch (InstantiationException e) {
			// This probably happened because the reader did not specify a default
			// constructor
			_writer.println("There was an error constructing the reader \"" + alias + "\"");
			e.printStackTrace(_writer);
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace(_writer);
			return null;
		}
	}
	
	/**
	 * Returns the Java class name of the ICommand by the given alias
	 * @param alias A registered alias for a command
	 * @return The class name of the command, or NULL
	 */
	public String getCommandClassName(String alias)	{
		return _commands.get(alias);
	}
	
	/**
	 * Sets the alias for the given command in this environment
	 * @param alias The alias by which to refer to the command
	 * @param className The name of the class for the command
	 */
	public void setCommandAlias(String alias, String className)	{
		_commands.put(alias, className);
	}
	
	/**
	 * Gets a set of all registered command aliases
	 * @return The set of aliases
	 */
	public Set<String> getCommandAliases()	{
		return _commands.keySet();
	}
	
	/**
	 * Creates a command object by the given alias
	 * @param alias The alias by which the command is referred
	 * @return The command object, or NULL if no command was found by that alias
	 */
	public ICommand createCommand(String alias)	{
		String className = this.getCommandClassName(alias);
		if (className == null || className.isEmpty())	{
			return null;
		}
		
		try {
			Class<?> theClass = Class.forName(className);
			ICommand cmd = (ICommand)theClass.newInstance();
			cmd.setWriter(_writer);
			return cmd;
			
		} catch (ClassNotFoundException e) {
			// The class name was registered, but doesn't really exist
			_writer.println("The command alias \"" + alias + "\" does not have a backing class");
			return null;
		} catch (InstantiationException e) {
			// This probably happened because the command did not specify a default
			// constructor
			_writer.println("There was an error constructing the command \"" + alias + "\"");
			e.printStackTrace(_writer);
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace(_writer);
			return null;
		}
	}
	
	/**
	 * Pushes an argument onto the environment's stack
	 * @param arg The argument to push
	 */
	public void push(String arg)	{
		_stack.push(arg);
	}
	
	/**
	 * Pops an argument off the environment's stack
	 * @return The argument, or null if the stack is empty
	 */
	public String pop()	{
		try	{
			return _stack.pop();
		} catch (EmptyStackException e)	{
			return null;
		}
	}
}
