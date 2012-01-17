package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.InputStream;
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
		
		InputStream sr = System.in;
		sr.mark(0);
		int c = sr.read();
		if (c == -1)	{
			sr.reset();
			env.pushString("Unexpected EOF");
			env.pushReader("error");
			env.pushCommand("read");
			return;
		}
		
		char ch = (char)c;
		if (!Character.isLetter(ch) && ch != '_')	{
			sr.reset();
			env.pushString("Unexpected Character");
			env.pushReader("error");
			env.pushCommand("read");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(ch);
		while (true)	{
			sr.mark(0);
			c = sr.read();
			if (c == -1)	{
				// It's up to upper level code to determine whether it's ok to 
				// have an EOF directly after a name
				System.err.println("r(Name): " + sb.toString());
				sr.reset();
				ISymbol sym = new com.breckinloggins.cx.dictionary.Symbol();
				sym.setName(sb.toString());
				env.push(sym);
				env.pushReader("terminator");
				env.pushCommand("read");
				return;
			}
			
			ch = (char)c;
			if (Character.isLetterOrDigit(ch) || ch == '_')	{
				sb.append(ch);
			}
			else
			{
				sr.reset();
				break;
			}
		}
		
		// Optionally eat up some whitespace
		// TODO: needs to be set by environment or other way to turn this off and on
		((Whitespace)env.getReader("whitespace")).read(env);
		
		System.err.println("r(Name): " + sb.toString());
		ISymbol sym = new com.breckinloggins.cx.dictionary.Symbol();
		sym.setName(sb.toString());
		env.push(sym);
		
		env.pushReader("discriminator");
		env.pushCommand("read");
	}

}
