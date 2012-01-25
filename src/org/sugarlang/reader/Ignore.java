/**
 * 
 */
package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VChar;

/**
 * Reads the first character, then ignores everything until that character is encountered again
 * @author bloggins
 */
public class Ignore extends BaseReader {

	public Ignore() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Reads the first character, then ignores everything until that character is read again";
	}

	@Override
	public void readInternal(Environment env) throws IOException, TypeException {
		readChar(env);
		if (!(env.peek() instanceof VChar))	{
			env.pushError("Argument on the stack is not a Char");
			return;
		}
		
		int c = ((VChar)env.peek()).getChar();
		if (c == -1)	{
			env.pop();
			env.pushError("Unexpected EOF");
			return;
		}
		
		char triggerChar = (char)c;
		env.pop();
		System.err.println("r(Ignore) - Ignoring until '" + Character.toString(triggerChar) + "'");
		
		while (true)	{
			readChar(env);
			if (!(env.peek() instanceof VChar))	{
				env.pushError("Argument on the stack is not a Char");
				return;
			}
			c = ((VChar)env.peek()).getChar();
			
			if (c == -1)	{
				env.pushError("Encounterer EOF before end ignore character");
				return;
			}
			
			env.pop();
			
			if ((char)c == triggerChar)	{
				break;
			}	
		}
		
		System.err.println("r(Ignore) - Done ignoring");
	}
}
