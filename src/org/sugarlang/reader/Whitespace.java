package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VChar;
import org.sugarlang.value.VWhitespace;


public class Whitespace extends BaseReader {

	public Whitespace() throws TypeException {
		super();
	}

	@Override
	public String getDescription()	{
		return "Reads one or more whitespace characters";
	}
	
	@Override
	public void readInternal(Environment env) throws IOException, TypeException {
		while (true)	{
			readChar(env);
			if (!(env.peek() instanceof VChar))	{
				return;
			}
			
			int ch = ((VChar)env.peek()).getChar();
			IValue v = env.getBoundObject(Character.toString((char)ch));
			if (null == v || !(v instanceof VWhitespace))	{
				break;
			}
			
			env.pop();
		}
	}
}
