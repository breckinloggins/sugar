package org.sugarlang;

import java.io.PrintStream;
import java.util.EmptyStackException;
import java.util.Set;
import java.util.Stack;

import org.sugarlang.base.IOp;
import org.sugarlang.base.IReader;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TMark;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VString;
import org.sugarlang.value.VSymbol;


public class Environment {
	
	private Dictionary _dictionary;
	private Stack<IValue> _stack;
	
	public Environment()	{		
		_dictionary = new Dictionary(null);
		_stack = new Stack<IValue>();
	}
	
	/**
	 * Sets a binding in the dictionary between a symbol and an arbitrary object
	 * @param sym The symbol to which to bind the object.  If a binding already exists for this symbol, it will be
	 * overwritten
	 * @param obj The object to set
	 * @throws TypeException Thrown if attempt to bind to an unsealed object
	 */
	public void setBinding(VSymbol sym, IValue obj) throws TypeException	{
		_dictionary.set(sym, obj);
	}
	
	/**
	 * Sets a binding in the dictionary between a symbol and an arbitrary object
	 * @param sym The symbol to which to bind the object.  If a binding already exists for this symbol, it will be
	 * overwritten
	 * @param obj The object to set, may be null to represent an unbound symbol
	 * @throws TypeException Thrown if attempt to bind to an unsealed object
	 */
	public void setBinding(String sym, IValue obj) throws TypeException	{
		VSymbol vsym = new VSymbol(sym);
		
		setBinding(vsym, obj);
	}
	
	/**
	 * Clears anything bound to the given symbol
	 * @param sym The symbol to unset
	 * @throws TypeException Thrown if the symbol is null or unsealed
	 */
	public void unsetBinding(VSymbol sym) throws TypeException	{
		_dictionary.unset(sym);
	}
	
	/**
	 * Gets a binding in the dictionary by the given symbol
	 * @param sym The symbol to look up
	 * @return null if the symbol is not bound, TNull if the symbol is bound to null, 
	 * the bound object otherwise
	 * @throws TypeException Thrown if the symbol is null or unseaded
	 */
	public IValue getBoundObject(VSymbol sym) throws TypeException	{
		return _dictionary.lookup(sym);
	}
	
	/**
	 * Gets a binding in the dictionary by the given symbol
	 * @param sym The symbol to look up
	 * @return null if the symbol is not bound, TNull if the symbol is bound to null, 
	 * the bound object otherwise
	 * @throws TypeException Thrown if sym is null
	 */
	public IValue getBoundObject(String sym) throws TypeException	{
		VSymbol vsym = new VSymbol(sym);
		return getBoundObject(vsym);
	}
	
	/**
	 * Gets the list of symbols bound in the current environment
	 * @return The symbol list
	 */
	public Set<VSymbol> getBindingSymbols()	{
		return _dictionary.getBindingSymbols();
	}
	
	/**
	 * Pushes an argument onto the environment's stack
	 * @param arg The argument to push
	 * @throws TypeException Thrown if attempt to push null or unsealed object
	 */
	public void push(IValue arg) throws TypeException	{
		if (null == arg)	{
			throw new TypeException("Cannot push null value onto stack.  Use TNull");
		}
		
		if (!arg.isSealed())	{
			throw new TypeException("Cannot push an unsealed object onto the stack");
		}
		
		_stack.push(arg);
	}
	
	/**
	 * Pushes a string onto the environment's stack
	 * @param s The string to push
	 * @throws TypeException Thrown if the string is null
	 */
	public void pushString(String s) throws TypeException	{
		_stack.push(new VString(s));
	}
	
	/**
	 * Pushes a symbol onto the environment's stack
	 * @param s The string of the symbol to push
	 * @throws TypeException Thrown if s is null
	 */
	public void pushSymbol(String s) throws TypeException	{
		VSymbol sym = new VSymbol(s);
		push(sym);
	}
	
	/**
	 * Pushes the opcode with the given alias onto the stack.  If there is no opcode
	 * by that alias, an error is pushed instead
	 * @param alias The alias by which the opcode is known in this environment
	 * @throws TypeException Thrown if alias is null
	 */
	public void pushOp(String alias) throws TypeException	{
		IValue v = getBoundObject(alias);
		if (null == v || !(v instanceof IOp))	{
			pushString("\"" + alias + "\" is not an opcode");
			push(new org.sugarlang.op.Error());
			return;
		}
		
		push(v);
	}
	
	/**
	 * Pushes the reader with the given alias onto the stack.  If there is no reader
	 * by that alias, an error is pushed instead
	 * @param alias The alias by which the reader is known in this environment
	 * @throws TypeException Thrown if alias is null 
	 */
	public void pushReader(String alias) throws TypeException	{
		IValue v = getBoundObject(alias);
		if (null == v || !(v instanceof IReader))	{
			pushString("\"" + alias + "\" is not a reader");
			push(new org.sugarlang.op.Error());
			return;
		}
		
		push(v);
	}
	
	/**
	 * Pushes the given reader onto the stack
	 * @param reader The reader to push onto the stack
	 * @throws TypeException Thrown if reader is null
	 */
	public void pushReader(IReader reader) throws TypeException	{
		push(reader);
	}
	
	/**
	 * Pushes objects from the given stack onto this environment's stack
	 * @param stack The stack to push.  Note that all items will be popped off of this stack
	 * @throws TypeException Thrown if any element in the stack is null
	 */
	public void pushStack(Stack<IValue> stack) throws TypeException	{
		while (!stack.isEmpty())	{
			push(stack.pop());
		}
	}
	
	/**
	 * Pushes a stack marker on the stack
	 */
	public void pushMark()	{
		try {
			push(new TMark());
		} catch (TypeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Pushes an error message on the stack
	 * @param message The message to push
	 */
	public void pushError(String message)	{
		if (null == message)	{
			message = "";
		}
		
		try	{
			pushString(message);
			pushOp("error");
		} catch (TypeException e)	{
			e.printStackTrace();
		}
	}
	
	/**
	 * Pops an argument off the environment's stack
	 * @return The argument, or null if the stack is empty
	 */
	public IValue pop()	{
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
	public Stack<IValue> popToMark()	{
		Stack<IValue> popped = new Stack<IValue>();
		
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
	public IValue peek()	{
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
	 * If the item at the top of the stack is an opcode, the opcode is evaluated, else the stack
	 * is left alone
	 * 
	 * @return The IOp that was evaluated, or null if the item wasn't an opcode
	 */
	public IOp evaluateStack()	{
		// TODO: This doesn't need to be built-in.  An evaluate command is fine for this
		// TODO: This also doesn't need to do only opcodes.  Any callable entity should suffice
		if (null == peek())	{
			// It's not an error to evaluate an empty stack
			return null;
		}
		
		if (peek() instanceof IOp)	{
			IOp op = (IOp)pop();
			op.execute(this);
			return op;
		}
		
		return null;
	}
	
	private void printStack(Stack<IValue> stack, PrintStream stream)	{
		if (stack.isEmpty())	{
			stream.println("[STACK EMPTY]");
			return;
		}
		
		for (int i = stack.size() - 1; i >= 0; i--)	{
			IValue entry = stack.get(i);
			String value = entry.toString();
			value = value.replace("\n", "\\n");
			value = value.replace("\t", "\\t");
			value = value.replace("\r", "\\r");
			stream.print("[" + (stack.size() - i - 1) + "] " + value + " <" + entry.getType().toString() + ">");
			stream.println();
		}
	}
	
	/**
	 * Print's the contents of the current stack to the environment's writer
	 */
	public void printStack(PrintStream stream)	{
		printStack(_stack, stream);
	}
}
