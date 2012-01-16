/**
 * 
 */
package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseReader;
import com.breckinloggins.cx.dictionary.IReader;
import com.breckinloggins.cx.dictionary.ISymbol;

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
	public IReader read(StringReader sr, Environment env) throws IOException {
		sr.mark(0);
		int c = sr.read();
		if (c == -1)	{
			sr.reset();
			Error e = (Error)env.createReader("error");
			e.setMessage("Unexpected");
			return e;
		}
		
		char ch = (char)c;
		if (Character.isWhitespace(ch))	{
			// TODO: Should be replaced by a dynamic definition of our whitespace set
			sr.reset();
			Error e = (Error)env.createReader("error");
			e.setMessage("Unexpected Whitespace");
			return e;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(ch);
		while (true)	{
			sr.mark(0);
			c = sr.read();
			if (c == -1)	{
				// It's up to upper level code to determine whether it's ok to 
				// have an EOF directly after a symbol
				getWriter().println("r(Symbol): " + sb.toString());
				sr.reset();
				ISymbol sym = new com.breckinloggins.cx.dictionary.Symbol();
				sym.setName(sb.toString());
				env.push(sym);
				return env.createReader("terminator");
			}
			
			ch = (char)c;
			if (!Character.isWhitespace(ch))	{
				sb.append(ch);
			}
			else
			{
				sr.reset();
				break;
			}
		}
		
		getWriter().println("r(Symbol): " + sb.toString());
		ISymbol sym = new com.breckinloggins.cx.dictionary.Symbol();
		sym.setName(sb.toString());
		env.push(sym);
		
		return env.createReader("discriminator");	}

}
