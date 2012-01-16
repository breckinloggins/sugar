package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseReader;
import com.breckinloggins.cx.dictionary.IReader;

public class Whitespace extends BaseReader {

	@Override
	public String getDescription()	{
		return "Reads one or more whitespace characters";
	}
	
	@Override
	public IReader read(StringReader sr, Environment env) throws IOException {
		// TODO: User should be able to configure what counts as whitespace and any special actions
		// that might occur (such as to support pythonic languages)
		sr.mark(0);
		int c = sr.read();
		if (c == -1)	{
			sr.reset();
			return env.createReader("terminator");
		}
		
		boolean hasWhitespace = false;
		
		while (Character.isWhitespace(c))	{
			hasWhitespace = true;
			sr.mark(0);
			c = sr.read();
		}
		
		if (hasWhitespace)	getWriter().println("r(Whitespace)");
		
		sr.reset();
		return env.createReader("discriminator");
	}

}
