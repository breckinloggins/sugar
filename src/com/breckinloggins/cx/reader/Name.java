package com.breckinloggins.cx.reader;

import java.io.IOException;
import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseReader;
import com.breckinloggins.cx.dictionary.ISymbol;

public class Name extends BaseReader {

	// TODO: The user needs to be able to define the acceptable character sequences
	// of a name
	
	@Override
	public String getDescription()	{
		return "Reads an identifier and pushes it onto the stack";
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
			env.pushCommand("error");
			return;
		}
		
		char ch = (char)c;
		if (!Character.isLetter(ch) && ch != '_')	{
			env.pop();
			env.pushString("Unexpected Character: '" + ch + "'" );
			env.pushCommand("error");
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
				// have an EOF directly after a name
				env.pop();
				
				System.err.println("r(Name): " + sb.toString());
				ISymbol sym = new com.breckinloggins.cx.type.TSymbol();
				sym.setName(sb.toString());
				env.push(sym);
				env.pushReader("terminator");
				env.pushCommand("read");
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
		
		System.err.println("r(Name): " + sb.toString());
		ISymbol sym = new com.breckinloggins.cx.type.TSymbol();
		sym.setName(sb.toString());
		env.push(sym);		
	}

}
