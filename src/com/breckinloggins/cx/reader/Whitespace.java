package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.InputStream;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseReader;

public class Whitespace extends BaseReader {

	@Override
	public String getDescription()	{
		return "Reads one or more whitespace characters";
	}
	
	@Override
	public void read(Environment env) throws IOException {
		
		InputStream sr = System.in;
		
		// TODO: User should be able to configure what counts as whitespace and any special actions
		// that might occur (such as to support pythonic languages)
		sr.mark(0);
		int c = sr.read();
		if (c == -1)	{
			sr.reset();
			env.pushReader("terminator");
			env.pushCommand("read");
			return;
		}
		
		boolean hasWhitespace = false;
		
		while (Character.isWhitespace(c))	{
			hasWhitespace = true;
			sr.mark(0);
			c = sr.read();
		}
		
		if (hasWhitespace)	System.err.println("r(Whitespace)");
		
		sr.reset();
		env.pushReader("discriminator");
		env.pushCommand("read");
	}

}
