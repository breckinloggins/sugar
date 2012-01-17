/**
 * 
 */
package com.breckinloggins.cx.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseCommand;
import com.breckinloggins.cx.dictionary.IEntry;
import com.breckinloggins.cx.dictionary.IReader;

/**
 * Executes the reader at the top of the stack
 * @author bloggins
 */
public class Read extends BaseCommand {

	@Override
	public String getDescription() {
		return "Executes the reader at the top of the stack";
	}

	@Override
	public void execute(Environment env) {
		IEntry arg = env.pop();
		if (null == arg)	{
			env.pushString("Cannot execute reader. stack empty");
			env.getCommand("error").execute(env);
			return;
		}
		
		if (!(arg instanceof IReader))	{
			env.pushString("Argument \"" + arg.getName() + "\" is not a reader");
			env.getCommand("error").execute(env);
			return;
		}
		
		IReader reader = (IReader)arg;
		
		// TODO: This is wrong, readers should read a character at a time
		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(converter);
		
		StringReader sr;
		try {
			sr = new StringReader(br.readLine());
			do {
				reader = reader.read(sr, env);
			} while (reader != null);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
