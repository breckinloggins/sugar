/**
 * 
 */
package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.base.IReader;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VChar;
import org.sugarlang.value.VWhitespace;

/**
 * Reads the minimum number of characters necessary to setup the environment so that typing is possible
 * @author bloggins
 */
public class Bootstrap extends BaseReader {
	
	public char _nullChar;
	
	public Bootstrap() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Reads the minimum number of characters necesary to setup the environment so that typing is possible";
	}

	@Override
	public void readInternal(Environment env) throws IOException, TypeException {
		System.err.println("Expecting character for Null");
		readNullCharacter(env);
		
		System.err.println("Expecting character for ignored input (comments).  Null to ignore");
		readIgnoredCharacter(env);
		
		System.err.println("Expecting character for stack quotation.  Null to ignore");
		readAndBindToReader(env, new org.sugarlang.reader.Quoted());
		
		System.err.println("Expecting characters to define as whitespace.  Null to finish");
		readWhitespace(env);
		
		System.err.println("System is bootstrapped; welcome to Sugar.");
	}
	
	private void readNullCharacter(Environment env) throws IOException, TypeException {
		readChar(env);
		if (!(env.peek() instanceof VChar))	{
			env.pushError("Argument on the stack is not a Char");
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
		
		env.pop();
		env.setBinding(Character.toString((char)ch), new org.sugarlang.op.Null());
		
		_nullChar = (char)ch;
		System.err.println(Character.toString((char)ch) + " => Null");
	}
	
	/**
	 * Returns whether we should ignore the given character, if so, prints the given messate to stderr
	 * @param ch The character to test
	 * @param message The message to print if we're ignoring
	 * @return True if we should ignore, false otherwise
	 */
	private boolean shouldIgnore(char ch, String message)	{
		boolean ret = ch == _nullChar;
		
		if (ret)	{
			System.err.println(message);
		}
		
		return ret;
	}
	
	/**
	 * Reads a character and immediately invokes the "ignored" reader after setting that 
	 * character as the symbol binding
	 * @param env
	 * @throws IOException
	 * @throws TypeException
	 */
	private void readIgnoredCharacter(Environment env) throws IOException, TypeException	{
		readChar(env);
		if (!(env.peek() instanceof VChar))	{
			env.pushError("Argument on the stack is not a Char");
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
		
		if (shouldIgnore((char)ch, "Not setting ignored character"))	{
			env.pop();
			return;
		}
		
		IReader ignored = new org.sugarlang.reader.Ignore();
		env.setBinding(Character.toString((char)ch), ignored);
		
		// We will invoke the reader now and leave that character on the stack
		// so ignored will know what to trigger by
		env.pushReader("ignore");
		env.pushOp("read");
		env.evaluateStack();
	}
	
	private void readAndBindToReader(Environment env, IReader reader) throws IOException, TypeException	{
		readChar(env);
		if (!(env.peek() instanceof VChar))	{
			env.pushError("Argument on the stack is not a Char");
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
		
		if (shouldIgnore((char)ch, "Not setting reader for " + reader.getClass().getName()))	{
			env.pop();
			return;
		}
		
		env.setBinding(Character.toString((char) ch), reader);
		env.pop();
		
		System.err.println("r(Bootstrap): " + Character.toString((char) ch) + " => " + reader.getClass().getName());	
	}
	
	private void readWhitespace(Environment env) throws TypeException	{
		while (true)	{
			readChar(env);
			if (!(env.peek() instanceof VChar))	{
				env.pushError("Argument on the stack is not a Char");
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
			
			env.pop();
			char c = (char)ch;
			if (shouldIgnore(c, "Finished with whitespace"))	{
				break;
			}
			
			VWhitespace vws = new VWhitespace(c);
			
			env.setBinding(Character.toString(c), vws);
			
			System.err.println("r(Bootstrap): " + vws.toString() + " => Whitespace");
		}
	}

}
