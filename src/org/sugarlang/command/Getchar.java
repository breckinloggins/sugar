/**
 * 
 */
package org.sugarlang.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseCommand;


/**
 * Gets the next character from standard input and pushes it on the stack.
 * @author bloggins
 */
public class Getchar extends BaseCommand {

	private InputStreamReader _inputReader;
	private BufferedReader _bufferedReader;
	
	public Getchar()	{
		_inputReader = new InputStreamReader(System.in);
		_bufferedReader = new BufferedReader(_inputReader);
	}
	
	@Override
	public String getDescription() {
		return "Gets the next character from standard input and pushes it on the stack.";
	}

	@Override
	public void execute(Environment env) {
		try {
			int ch = _bufferedReader.read();
			env.push(ch);
		} catch (IOException e) {
			env.pushString("Cannot read from standard input");
			env.pushCommand("error");
		}
	}

}