package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.base.IReader;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VChar;
import org.sugarlang.value.VMacro;


public class Discriminator extends BaseReader {

	public Discriminator() throws TypeException {
		super();
	}

	@Override
	public String getDescription()	{
		return "Reads enough characters to determine the most probable next reader";
	}
	
	@Override
	public void readInternal(Environment env) throws IOException, TypeException {
		
		readChar(env);
		if (!(env.peek() instanceof VChar))	{
			return;
		}
		
		int ch = ((VChar)env.peek()).getChar();
		if (ch == -1)	{
			// End of stream, send the Terminator
			env.pop();
			env.pushReader("terminator");
			env.pushOp("read");
			return;
		}
		
		System.err.println("r(Discriminator): " + Character.toString((char) ch));
		
		IValue v = env.getBoundObject(Character.toString((char)ch));
		if (null != v && v instanceof IReader)	{
			env.pop();
			env.pushReader((IReader)v);
			env.pushOp("read");
		} else if (null != v && v instanceof VMacro)	{
			env.pop();
			env.push(v);
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
