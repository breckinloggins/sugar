/**
 * 
 */
package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.base.IReader;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VChar;

/**
 * Reads the minimum number of characters necessary to setup the environment so that typing is possible
 * @author bloggins
 */
public class Bootstrap extends BaseReader {

	public Bootstrap() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Reads the minimum number of characters necesary to setup the environment so that typing is possible";
	}

	@Override
	public void readInternal(Environment env) throws IOException, TypeException {
		readAndBindToReader(env, new org.sugarlang.reader.Command());
		readAndBindToReader(env, new org.sugarlang.reader.Quoted());
	}
	
	private void readAndBindToReader(Environment env, IReader reader) throws IOException, TypeException	{
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
		
		
		env.setBinding(Character.toString((char) ch), reader);
		env.pop();
		
		System.err.println("r(Bootstrap): " + Character.toString((char) ch) + " => " + reader.getClass().getName());	
	}

}
