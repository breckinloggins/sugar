/**
 * 
 */
package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VChar;
import org.sugarlang.value.VSymbol;
import org.sugarlang.value.VWhitespace;


/**
 * Represents one or more non-whitespace characters (with the definition of "whitespace" as defined in the current environment")
 * @author bloggins
 */
public class Symbol extends BaseReader {

	public Symbol() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Reads one or more non-WHITESPACE characters and pushes them onto the stack";
	}

	@Override
	public void readInternal(Environment env) throws IOException, TypeException {
		
		readChar(env);
		if (!(env.peek() instanceof VChar))	{
			return;
		}
		
		int c = ((VChar)env.peek()).getChar();
		if (c == -1)	{
			env.pop();
			env.pushError("Unexpected EOF");
			return;
		}
		
		char ch = (char)c;
		IValue v = env.getBoundObject(Character.toString((char)ch));
		if (null != v && v instanceof VWhitespace)	{
			env.pushError("Unexpected Whitespace");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		while (true)	{
			readChar(env);
			if (!(env.peek() instanceof VChar))	{
				return;
			}
			c = ((VChar)env.peek()).getChar();
			
			if (c == -1)	{
				// It's up to upper level code to determine whether it's ok to 
				// have an EOF directly after a symbol
				env.pop();
				System.err.println("r(Symbol): " + sb.toString());
				VSymbol sym = new org.sugarlang.value.VSymbol(sb.toString());
				env.push(sym);
				env.pushReader("terminator");
				env.pushOp("read");
				return;
			}
			
			ch = (char)c;
			v = env.getBoundObject(Character.toString((char)ch));
			env.pop();
			
			if (null != v && v instanceof VWhitespace)	{
				break;
			}
			
			sb.append(ch);
				
		}
		
		System.err.println("r(Symbol): " + sb.toString());
		VSymbol sym = new org.sugarlang.value.VSymbol(sb.toString());
		env.push(sym);
	}
}
