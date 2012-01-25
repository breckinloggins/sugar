/**
 * 
 */
package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.base.IReader;
import org.sugarlang.base.IType;
import org.sugarlang.type.TReader;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VChar;


/**
 * @author bloggins
 * Base functionality for all readers
 */
public abstract class BaseReader implements IReader {
	
	private boolean _isSealed = false;
	private IType _type;
	
	public BaseReader() throws TypeException	{
		setType(new TReader());
		
		seal();
	}
	
	protected void setType(IType type) throws TypeException	{
		throwIfSealed();
		
		_type = type;
	}
	
	@Override
	public IType getType() {
		return _type;
	}

	@Override
	public void seal() throws TypeException {
		throwIfSealed();
		_isSealed = true;
	}

	@Override
	public boolean isSealed() {
		return _isSealed;
	}

	/*
	 * @see com.breckinloggins.cx.IEntry#getDescription()
	 */
	@Override
	public String getDescription()	{
		return "(reader)";
	}
	
	/* 
	 * @see com.breckinloggins.cx.reader.IReader#read(com.breckinloggins.cx.Environment)
	 */
	@Override
	final public void read(Environment env) throws IOException {
		try	{
			readInternal(env);
		} catch (TypeException e)	{
			e.printStackTrace();
			env.pushError(e.getMessage());
		}
	}
	
	/**
	 * Override to perform actual reading
	 * @param env The environment in which to perform the read
	 * @throws IOException
	 * @throws TypeException
	 */
	protected abstract void readInternal(Environment env) throws IOException, TypeException;

	/**
	 * Reads a character from standard input and pushes it on the stack.  If the top of the 
	 * stack already has an integer, assumes it is a character and leaves it there.
	 * @param env The environment to run the commands
	 * @return The character, -1 if EOF, -2 if Error
	 */
	protected void readChar(Environment env) throws TypeException	{
		if (!env.isStackEmpty() && (env.peek() instanceof VChar))	{
			// We have a character on top of the stack.  Assume it's a character and leave it
			return;
		}
		
		env.pushOp("getchar");
		env.evaluateStack();
	}
	
	/**
	 * If there is a whitespace character on the stack, discards it
	 * @param env The environment to examine
	 */
	protected void discardWhitespace(Environment env)	{
		if (!env.isStackEmpty() && (env.peek() instanceof VChar))	{
			if (Character.isWhitespace(((VChar)env.peek()).getChar()))	{
				env.pop();
			}
		}
	}
	
	/**
	 * Throws a TypeException if the object is sealed
	 * @throws TypeException Thrown if object is sealed
	 */
	protected void throwIfSealed() throws TypeException	{
		if (_isSealed)	{
			throw new TypeException("Invalid operation; object is sealed");
		}
	}
}
