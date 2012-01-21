/**
 * 
 */
package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseReader;
import org.sugarlang.type.TSymbol;


/**
 * Represents one or more non-whitespace characters (with the definition of "whitespace" as defined in the current environment")
 * @author bloggins
 */
public class Symbol extends BaseReader {

	@Override
	public String getDescription() {
		return "Reads one or more non-WHITESPACE characters and pushes them onto the stack";
	}

	@Override
	public void read(Environment env) throws IOException {
		
		readChar(env);
		if (!(env.peek() instanceof Integer))	{
			return;
		}
		
		int c = (Integer)env.peek();
		if (c == -1)	{
			env.pop();
			env.pushString("Unexpected EOF");
			env.pushOp("error");
			return;
		}
		
		char ch = (char)c;
		if (Character.isWhitespace(ch))	{
			// TODO: Should be replaced by a dynamic definition of our whitespace set
			env.pop();
			env.pushString("Unexpected Whitespace");
			env.pushOp("error");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		while (true)	{
			readChar(env);
			if (!(env.peek() instanceof Integer))	{
				return;
			}
			c = (Integer)env.peek();
			
			if (c == -1)	{
				// It's up to upper level code to determine whether it's ok to 
				// have an EOF directly after a symbol
				env.pop();
				System.err.println("r(Symbol): " + sb.toString());
				TSymbol sym = new org.sugarlang.type.TSymbol(sb.toString());
				env.push(sym);
				env.pushReader("terminator");
				env.pushOp("read");
				return;
			}
			
			ch = (char)c;
			if (!Character.isWhitespace(ch))	{
				sb.append(ch);
				env.pop();
			}
			else
			{
				break;
			}	
		}
		
		discardWhitespace(env);
		
		System.err.println("r(Symbol): " + sb.toString());
		TSymbol sym = new org.sugarlang.type.TSymbol(sb.toString());
		env.push(sym);
	}
}
