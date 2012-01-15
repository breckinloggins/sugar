package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;

import com.breckinloggins.cx.Environment;

public class Whitespace extends BaseReader {

	@Override
	public IReader read(StringReader sr, Environment env) throws IOException {
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
