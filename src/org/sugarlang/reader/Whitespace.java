package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VChar;


public class Whitespace extends BaseReader {

	public Whitespace() throws TypeException {
		super();
	}

	@Override
	public String getDescription()	{
		return "Reads one or more whitespace characters";
	}
	
	@Override
	public void readInternal(Environment env) throws IOException, TypeException {
		
		readChar(env);
		if (!(env.peek() instanceof VChar))	{
			return;
		}
		
		// TODO: User should be able to configure what counts as whitespace and any special actions
		// that might occur (such as to support pythonic languages)
		int c = ((VChar)env.peek()).getChar();
		if (c == -1)	{
			env.pop();
			env.pushReader("terminator");
			env.pushOp("read");
			return;
		}
		
		boolean hasWhitespace = false;
		
		while (Character.isWhitespace(c))	{
			hasWhitespace = true;
			env.pop();
			
			readChar(env);
			if (!(env.peek() instanceof VChar))	{
				return;
			}
			c = ((VChar)env.peek()).getChar();
		}
		
		if (hasWhitespace)	System.err.println("r(Whitespace)");
		
		env.pushReader("discriminator");
		env.pushOp("read");
	}

}
