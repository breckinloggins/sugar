package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;

import com.breckinloggins.cx.Environment;

public class Whitespace implements IReader {

	@Override
	public IReader read(StringReader sr, Environment env) throws IOException {
		sr.mark(0);
		int c = sr.read();
		if (c == -1)	{
			sr.reset();
			return new Terminator();
		}
		
		boolean hasWhitespace = false;
		
		while (Character.isWhitespace(c))	{
			hasWhitespace = true;
			sr.mark(0);
			c = sr.read();
		}
		
		if (hasWhitespace)	System.out.println("r(Whitespace");
		
		sr.reset();
		return new Discriminator();
	}

}
