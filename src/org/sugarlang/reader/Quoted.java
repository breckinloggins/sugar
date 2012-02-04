/**
 * 
 */
package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VChar;

/**
 * Reads the next item (up to whitespace) and pushes it on the stack in quoted form
 * @author bloggins
 */
public class Quoted extends BaseReader {
	
	public Quoted() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Reads the next item (up to whitespace) and pushes it on the stack in quoted form";
	}

	@Override
	public void readInternal(Environment env) throws IOException, TypeException {
		// TODO: This could be much more featureful if combined with a new environment
		readChar(env);
		if (!(env.peek() instanceof VChar))	{
			return;
		}
		
		int ch = ((VChar)env.peek()).getChar();
		if (ch == -1)	{
			// End of stream, send the Terminator
			env.pop();
			env.pushReader("terminator");
			env.pushOp("read");
			return;
		}
		
		IValue v = env.getBoundObject(Character.toString((char)ch));
		if (v instanceof Quoted)	{
			env.pop();
			readInternal(env);
		} else {
			env.pushReader("symbol");
			env.pushOp("read");
		}
		
		env.evaluateStack();
		
		// If we have something on the top of the stack, quote it
		if (!env.isStackEmpty())	{
			env.pushOp("quote");
		}
	}
}
