package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VChar;
import org.sugarlang.value.VSymbol;


public class Name extends BaseReader {

	// TODO: The user needs to be able to define the acceptable character sequences
	// of a name
	
	public Name() throws TypeException {
		super();
	}

	@Override
	public String getDescription()	{
		return "Reads an identifier and pushes it onto the stack";
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
		if (!Character.isLetter(ch) && ch != '_')	{
			env.pop();
			env.pushError("Unexpected Character: '" + ch + "'" );
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
				// have an EOF directly after a name
				env.pop();
				
				System.err.println("r(Name): " + sb.toString());
				VSymbol sym = new org.sugarlang.value.VSymbol(sb.toString());
				env.push(sym);
				env.pushReader("terminator");
				env.pushOp("read");
				return;
			} else if (c == -2)	{
				// We have an error
				return;
			}
			
			ch = (char)c;
			if (Character.isLetterOrDigit(ch) || ch == '_')	{
				env.pop();
				sb.append(ch);
			}
			else
			{
				break;
			}
		}
		
		discardWhitespace(env);
		
		System.err.println("r(Name): " + sb.toString());
		VSymbol sym = new org.sugarlang.value.VSymbol(sb.toString());
		env.push(sym);		
	}

}
