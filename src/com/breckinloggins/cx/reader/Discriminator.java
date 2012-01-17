package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.InputStream;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseReader;

public class Discriminator extends BaseReader {

	@Override
	public String getDescription()	{
		return "Reads enough characters to determine the most probable next reader";
	}
	
	@Override
	public void read(Environment env) throws IOException {
		InputStream sr = System.in;
		
		sr.mark(0);
		int ch = sr.read();
		if (ch == -1)	{
			// End of stream, send the Terminator
			env.pushReader("terminator");
			env.pushCommand("read");
			return;
		}
		
		System.err.println("r(Discriminator): " + Character.toString((char) ch));
		
		// TODO: These know WAY too much about what each reader wants.  Instead, the discriminator should
		// somehow be able to ask each reader if it will accept the input.  It would be nice to do this without
		// having created the readers ahead of time.  In case multiple readers accept, there should be some way
		// for them to "duke it out", perhaps with precedence or implicit precedence by voting.
		if (ch == '#')	{
			sr.reset();
			env.pushReader("reader");
			env.pushCommand("read");
			return;
		} else if (ch == '!')	{
			env.pushReader("command");
			env.pushCommand("read");
			return;
		} else if (Character.isLetter(ch) || ch == '_')	{
			sr.reset();
			env.pushReader("name");
			env.pushCommand("read");
			return;
		}
		
		env.pushReader("discriminator");
		env.pushCommand("read");
	}

}
