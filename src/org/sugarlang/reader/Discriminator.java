package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.base.IReader;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VChar;
import org.sugarlang.value.VMacro;
import org.sugarlang.value.VSymbol;
import org.sugarlang.value.VWhitespace;


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
			if (!(v instanceof Ignore))	{
				// HACK: Shouldn't have to do a reader-specific test here
				// TODO: Have each reader configure whether or not it wants the first
				// character
				env.pop();
			}
			env.pushReader((IReader)v);
			env.pushOp("read");
		} else if (null != v && v instanceof VMacro)	{
			// We don't push the macro itself, instead we push the symbol
			// bound to the macro and let the stack evaluator do its job
			VSymbol sym = new VSymbol(Character.toString((char)ch));
			env.pop();
			env.push(sym);
			env.evaluateStack();
		} else if (null != v && v instanceof VWhitespace)	{
			env.pop();
			env.pushReader("discriminator");
			env.pushOp("read");
		} else {
			env.pushReader("symbol");
			env.pushOp("read");	
		}
	}

}
