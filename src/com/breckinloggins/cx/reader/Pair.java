package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;
import com.breckinloggins.cx.Environment;

public class Pair extends BaseReader {
	
	@Override
	public String getDescription()	{
		return "Reads a balanced pair (such as '(foo bar baz)' or '<stuff>')";
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
		
		// Read of the form:
		// #pair <>
		// So we need to read some whitespace
		
		
		return null;
	}

}
