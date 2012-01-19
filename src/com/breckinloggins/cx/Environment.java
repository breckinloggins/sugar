package com.breckinloggins.cx;

import java.io.PrintStream;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

import com.breckinloggins.cx.dictionary.ICommand;
import com.breckinloggins.cx.dictionary.IEntry;
import com.breckinloggins.cx.dictionary.IReader;
import com.breckinloggins.cx.type.TNull;
import com.breckinloggins.cx.type.TSymbol;

public class Environment {
	
	private HashMap<String, IReader> _readers;
	private HashMap<String, ICommand> _commands;
	
	private HashMap<TSymbol, Object> _dictionary;
	private Stack<Object> _stack;
	
	public Environment()	{
		_readers = new HashMap<String, IReader>();
		_commands = new HashMap<String, ICommand>();
		
		_dictionary = new HashMap<TSymbol, Object>();
		_stack = new Stack<Object>();
	}
	
	/**
	 * Sets the alias for the given reader in this environment
	 * @param alias The alias by which to refer to the reader
	 * @param reader The name of the class for the reader
	 */
	public void setReader(String alias, IReader reader)	{
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
	 * @param alias The alias by which to refer to the command
	 * @param command The name of the class for the command
	 */
	public void setCommand(String alias, ICommand command)	{
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
	 * Sets a binding in the dictionary between a symbol and an arbitrary object
	 * @param sym The symbol to which to bind the object.  If a binding already exists for this symbol, it will be
	 * overwritten
	 * @param obj The object to set, may be null to represent an unbound symbol
	 */
	public void setBinding(TSymbol sym, Object obj)	{
		if (null == obj)	{
			obj = new TNull();
		}
		
		_dictionary.put(sym, obj);
	}
	
	/**
	 * Clears anything bound to the given symbol
	 * @param sym The symbol to unset
	 */
	public void unsetBinding(TSymbol sym)	{
		_dictionary.remove(sym);
	}
	
	/**
	 * Gets a binding in the dictionary by the given symbol
	 * @param sym The symbol to look up
	 * @return null if the symbol is not bound, TNull if the symbol is bound to null, 
	 * the bound object otherwise
	 */
	public Object getBoundObject(TSymbol sym)	{
		return _dictionary.get(sym);
	}
	
	/**
	 * Gets the list of symbols bound in the current environment
	 * @return The symbol list
	 */
	public Set<TSymbol> getBindingSymbols()	{
		return _dictionary.keySet();
	}
	
	/**
	 * Pushes an argument onto the environment's stack
	 * @param arg The argument to push
	 */
	public void push(Object arg)	{
		_stack.push(arg);
	}
	
	/**
	 * Pushes a string onto the environment's stack
	 * @param s The string to push
	 */
	public void pushString(String s)	{
		_stack.push(s);
	}
	
	/**
	 * Pushes a symbol onto the environment's stack
	 * @param s The string of the symbol to push
	 */
	public void pushSymbol(String s)	{
		TSymbol sym = new TSymbol();
		sym.setName(s);
		push(sym);
	}
	
	/**
	 * Pushes the command with the given alias onto the stack
	 * @param alias The alias by which the command is known in this environment
	 */
	public void pushCommand(String alias)	{
		ICommand cmd = getCommand(alias);
		push((IEntry)cmd);
	}
	
	/**
	 * Pushes the reader with the given alias onto the stack
	 * @param alias The alias by which the reader is known in this environment
	 */
	public void pushReader(String alias)	{
		IReader reader = getReader(alias);
		push((IEntry)reader);
	}
	
	/**
	 * Pushes the given reader onto the stack
	 * @param reader The reader to push onto the stack
	 */
	public void pushReader(IReader reader)	{
		push((IEntry)reader);
	}
	
	/**
	 * Pops an argument off the environment's stack
	 * @return The argument, or null if the stack is empty
	 */
	public Object pop()	{
		try	{
			return _stack.pop();
		} catch (EmptyStackException e)	{
			return null;
		}
	}
	
	/**
	 * Gets the element off the top of the stack without removing it from the stack
	 * @return The top element, or null if the stack is empty
	 */
	public Object peek()	{
		if (_stack.isEmpty())	{
			return null;
		}
		
		return _stack.peek();
	}
	
	/**
	 * Gets whether this environment's stack is empty
	 * @return True if empty, false if not
	 */
	public boolean isStackEmpty()	{
		return _stack.isEmpty();
	}
	
	/**
	 * If the item at the top of the stack is a command, the command is evaluated, else the stack
	 * is left alone
	 * 
	 * @return The ICommand that was evaluated, or null if the item wasn't a command
	 */
	public ICommand evaluateStack()	{
		// TODO: This doesn't need to be built-in.  An evaluate command is fine for this
		if (null == peek())	{
			// It's not an error to evaluate an empty stack
			return null;
		}
		
		if (peek() instanceof ICommand)	{
			ICommand cmd = (ICommand)pop();
			cmd.execute(this);
			return cmd;
		}
		
		return null;
	}
	
	/**
	 * Print's the contents of the current stack to the environment's writer
	 */
	public void printStack(PrintStream stream)	{
		
		if (isStackEmpty())	{
			stream.println("[STACK EMPTY]");
			return;
		}
		
		for (int i = _stack.size() - 1; i >= 0; i--)	{
			Object entry = _stack.get(i);
			String value = entry.toString();
			value = value.replace("\n", "\\n");
			value = value.replace("\t", "\\t");
			value = value.replace("\r", "\\r");
			stream.print("[" + (_stack.size() - i - 1) + "] " + value + " <" + entry.getClass().getName() + ">");
			stream.println();
		}
	}
}
