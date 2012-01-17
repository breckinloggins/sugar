/**
 * 
 */
package com.breckinloggins.cx.command;

import java.io.IOException;

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
		try {
			reader.read(env);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
