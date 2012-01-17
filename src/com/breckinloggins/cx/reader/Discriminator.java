package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;
import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseReader;
import com.breckinloggins.cx.dictionary.IReader;

public class Discriminator extends BaseReader {

	@Override
	public String getDescription()	{
		return "Reads enough characters to determine the most probably next reader";
	}
	
	@Override
	public IReader read(StringReader sr, Environment env) throws IOException {
		sr.mark(0);
		int ch = sr.read();
		if (ch == -1)	{
			// End of stream, send the Terminator
			return env.getReader("terminator");
		}
		
		System.err.println("r(Discriminator): " + Character.toString((char) ch));
		
		// TODO: These know WAY too much about what each reader wants.  Instead, the discriminator should
		// somehow be able to ask each reader if it will accept the input.  It would be nice to do this without
		// having created the readers ahead of time.  In case multiple readers accept, there should be some way
		// for them to "duke it out", perhaps with precedence or implicit precedence by voting.
		if (ch == '#')	{
			sr.reset();
			return env.getReader("reader");
		} else if (ch == '!')	{
			return env.getReader("command");
		} else if (Character.isLetter(ch) || ch == '_')	{
			sr.reset();
			return env.getReader("name");
		}
		
		return env.getReader("discriminator");
	}

}
