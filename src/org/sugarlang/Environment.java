package org.sugarlang;

import java.io.PrintStream;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

import org.sugarlang.dictionary.ICommand;
import org.sugarlang.dictionary.IEntry;
import org.sugarlang.dictionary.IReader;
import org.sugarlang.type.TMark;
import org.sugarlang.type.TNull;
import org.sugarlang.type.TSymbol;


public class Environment {
	
	private HashMap<TSymbol, Object> _dictionary;
	private Stack<Object> _stack;
	
	public Environment()	{		
		_dictionary = new HashMap<TSymbol, Object>();
		_stack = new Stack<Object>();
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
	 * Sets a binding in the dictionary between a symbol and an arbitrary object
	 * @param sym The symbol to which to bind the object.  If a binding already exists for this symbol, it will be
	 * overwritten
	 * @param obj The object to set, may be null to represent an unbound symbol
	 */
	public void setBinding(String sym, Object obj)	{
		TSymbol tsym = new TSymbol();
		tsym.setName(sym);
		
		setBinding(tsym, obj);
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
	 * Gets a binding in the dictionary by the given symbol
	 * @param sym The symbol to look up
	 * @return null if the symbol is not bound, TNull if the symbol is bound to null, 
	 * the bound object otherwise
	 */
	public Object getBoundObject(String sym)	{
		TSymbol tsym = new TSymbol();
		tsym.setName(sym);
		return getBoundObject(tsym);
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
	 * Pushes the command with the given alias onto the stack.  If there is no command
	 * by that alias, an error is pushed instead
	 * @param alias The alias by which the command is known in this environment
	 */
	public void pushCommand(String alias)	{
		Object o = getBoundObject(alias);
		if (null == o || !(o instanceof ICommand))	{
			pushString("\"" + alias + "\" is not a command");
			push(new org.sugarlang.command.Error());
			return;
		}
		
		push(o);
	}
	
	/**
	 * Pushes the reader with the given alias onto the stack.  If there is no reader
	 * by that alias, an error is pushed instead
	 * @param alias The alias by which the reader is known in this environment
	 */
	public void pushReader(String alias)	{
		Object o = getBoundObject(alias);
		if (null == o || !(o instanceof IReader))	{
			pushString("\"" + alias + "\" is not a reader");
			push(new org.sugarlang.command.Error());
			return;
		}
		
		push(o);
	}
	
	/**
	 * Pushes the given reader onto the stack
	 * @param reader The reader to push onto the stack
	 */
	public void pushReader(IReader reader)	{
		push((IEntry)reader);
	}
	
	/**
	 * Pushes objects from the given stack onto this environment's stack
	 * @param stack The stack to push.  Note that all items will be popped off of this stack
	 */
	public void pushStack(Stack<Object> stack)	{
		while (!stack.isEmpty())	{
			push(stack.pop());
		}
	}
	
	/**
	 * Pushes a stack marker on the stack
	 */
	public void pushMark()	{
		push(new TMark());
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
	 * Pops arguments off the stack until a TMark is found (which is also popped), or the stack is empty
	 * @return The stack of objects removed, minus the TMark
	 */
	public Stack<Object> popToMark()	{
		Stack<Object> popped = new Stack<Object>();
		
		while (!isStackEmpty())	{
			Object o = peek();
			if (o instanceof TMark)	{
				pop();
				break;
			}
			
			popped.push(pop());
		}
		
		return popped;
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
