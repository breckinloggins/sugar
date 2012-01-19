package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseReader;


public class Whitespace extends BaseReader {

	@Override
	public String getDescription()	{
		return "Reads one or more whitespace characters";
	}
	
	@Override
	public void read(Environment env) throws IOException {
		
		readChar(env);
		if (!(env.peek() instanceof Integer))	{
			return;
		}
		
		// TODO: User should be able to configure what counts as whitespace and any special actions
		// that might occur (such as to support pythonic languages)
		int c = (Integer)env.peek();
		if (c == -1)	{
			env.pop();
			env.pushReader("terminator");
			env.pushCommand("read");
			return;
		}
		
		boolean hasWhitespace = false;
		
		while (Character.isWhitespace(c))	{
			hasWhitespace = true;
			env.pop();
			
			readChar(env);
			if (!(env.peek() instanceof Integer))	{
				return;
			}
			c = (Integer)env.peek();
		}
		
		if (hasWhitespace)	System.err.println("r(Whitespace)");
		
		env.pushReader("discriminator");
		env.pushCommand("read");
	}

}
