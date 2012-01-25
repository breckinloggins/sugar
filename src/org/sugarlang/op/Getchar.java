/**
 * 
 */
package org.sugarlang.op;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VChar;


/**
 * Gets the next character from standard input and pushes it on the stack.
 * @author bloggins
 */
public class Getchar extends BaseOp {

	private InputStreamReader _inputReader;
	private BufferedReader _bufferedReader;
	
	public Getchar() throws TypeException	{
		super();
		
		_inputReader = new InputStreamReader(System.in);
		_bufferedReader = new BufferedReader(_inputReader);
	}
	
	@Override
	public String getDescription() {
		return "Gets the next character from standard input and pushes it on the stack.";
	}

	@Override
	public void executeInternal(Environment env) throws TypeException {
		try {
			int ch = _bufferedReader.read();
			env.push(new VChar(ch));
		} catch (IOException e) {
			env.pushError("Cannot read from standard input");
		}
	}

}
