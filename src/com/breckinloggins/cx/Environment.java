package com.breckinloggins.cx;

import java.io.PrintStream;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

import com.breckinloggins.cx.dictionary.ICommand;
import com.breckinloggins.cx.dictionary.IEntry;
import com.breckinloggins.cx.dictionary.IReader;
import com.breckinloggins.cx.dictionary.TString;

public class Environment {
	
	private HashMap<String, IReader> _readers;
	private HashMap<String, ICommand> _commands;
	private PrintStream _writer;
	private Stack<IEntry> _stack;
	
	public Environment(PrintStream writer)	{
		_readers = new HashMap<String, IReader>();
		_commands = new HashMap<String, ICommand>();
		_writer = writer;
		_stack = new Stack<IEntry>();
	}
	
	/**
	 * Sets the alias for the given reader in this environment
	 * Note that the reader's writer is also set to the environment's writer
	 * @param alias The alias by which to refer to the reader
	 * @param reader The name of the class for the reader
	 */
	public void setReader(String alias, IReader reader)	{
		reader.setWriter(_writer);
		_readers.put(alias, reader);
	}
	
	/**
	 * Gets a set of all registered reader aliases
	 * @return The set of aliases
	 */
	public Set<String> getReaderAliases()	{
		return _readers.keySet();
	}
	
	/**
	 * Gets the reader by the given alias
	 * @param alias The alias by which the reader is known
	 * @return The reader, or null if not found
	 */
	public IReader getReader(String alias)	{
		return _readers.get(alias);
	}
	
	/**
	 * Sets the alias for the given command in this environment
	 * Note that the command's writer is also set to the environment's writer
	 * @param alias The alias by which to refer to the command
	 * @param command The name of the class for the command
	 */
	public void setCommand(String alias, ICommand command)	{
		command.setWriter(_writer);
		_commands.put(alias, command);
	}
	
	/**
	 * Gets the command by the given alias
	 * @param alias The alias by which the command is known
	 * @return The command, or null if not found
	 */
	public ICommand getCommand(String alias)	{
		return _commands.get(alias);
	}
	
	/**
	 * Gets a set of all registered command aliases
	 * @return The set of aliases
	 */
	public Set<String> getCommandAliases()	{
		return _commands.keySet();
	}
	
	/**
	 * Pushes an argument onto the environment's stack
	 * @param arg The argument to push
	 */
	public void push(IEntry arg)	{
		_stack.push(arg);
	}
	
	/**
	 * Pushes a string onto the environment's stack
	 * @param s The string to push
	 */
	public void pushString(String s)	{
		_stack.push(new TString(s));
	}
	
	/**
	 * Pops an argument off the environment's stack
	 * @return The argument, or null if the stack is empty
	 */
	public IEntry pop()	{
		try	{
			return _stack.pop();
		} catch (EmptyStackException e)	{
			return null;
		}
	}
}
