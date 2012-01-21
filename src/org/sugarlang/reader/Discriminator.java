package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseReader;


public class Discriminator extends BaseReader {

	@Override
	public String getDescription()	{
		return "Reads enough characters to determine the most probable next reader";
	}
	
	@Override
	public void read(Environment env) throws IOException {
		
		readChar(env);
		if (!(env.peek() instanceof Integer))	{
			return;
		}
		
		int ch = (Integer)env.peek();
		if (ch == -1)	{
			// End of stream, send the Terminator
			env.pop();
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
			env.pop();
			env.pushReader("symbol");
			env.pushCommand("read");
			env.pushCommand("reader");
			env.pushCommand("read");
			return;
		} else if (ch == '`')	{  
			env.pop();
			env.pushReader("quoted");
			env.pushCommand("read");
		} else if (ch == '!')	{
			env.pop();
			env.pushReader("command");
			env.pushCommand("read");
			return;
		} else if (Character.isLetter(ch) || ch == '_')	{
			env.pushReader("name");
			env.pushCommand("read");
			return;
		} else if (Character.isWhitespace(ch))	{
			env.pushReader("whitespace");
			env.pushCommand("read");
		} else {
			env.pushReader("symbol");
			env.pushCommand("read");	
		}
	}

}
