package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseReader;
import org.sugarlang.dictionary.IReader;
import org.sugarlang.type.TMacro;


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
			env.pushOp("read");
			return;
		}
		
		System.err.println("r(Discriminator): " + Character.toString((char) ch));
		
		Object o = env.getBoundObject(Character.toString((char)ch));
		if (null != o && o instanceof IReader)	{
			env.pop();
			env.pushReader((IReader)o);
			env.pushOp("read");
		} else if (null != o && o instanceof TMacro)	{
			env.pop();
			env.push(o);
			env.pushOp("execute");
		} else if (Character.isLetter(ch) || ch == '_')	{
			env.pushReader("name");
			env.pushOp("read");
			return;
		} else if (Character.isWhitespace(ch))	{
			env.pushReader("whitespace");
			env.pushOp("read");
		} else {
			env.pushReader("symbol");
			env.pushOp("read");	
		}
	}

}
