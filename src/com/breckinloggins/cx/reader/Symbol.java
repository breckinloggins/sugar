/**
 * 
 */
package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.InputStream;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseReader;
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
		if (Character.isWhitespace(ch))	{
			// TODO: Should be replaced by a dynamic definition of our whitespace set
			sr.reset();
			env.pushString("Unexpected Whitespace");
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
				// have an EOF directly after a symbol
				System.err.println("r(Symbol): " + sb.toString());
				sr.reset();
				ISymbol sym = new com.breckinloggins.cx.dictionary.Symbol();
				sym.setName(sb.toString());
				env.push(sym);
				env.pushReader("terminator");
				env.pushCommand("read");
				return;
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
		
		System.err.println("r(Symbol): " + sb.toString());
		ISymbol sym = new com.breckinloggins.cx.dictionary.Symbol();
		sym.setName(sb.toString());
		env.push(sym);
		env.pushReader("discriminator");
		env.pushCommand("read");
	}
}
