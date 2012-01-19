/**
 * 
 */
package org.sugarlang.command;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseCommand;
import org.sugarlang.dictionary.IReader;


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
		Object arg = env.pop();
		if (null == arg)	{
			env.pushString("Cannot execute reader. stack empty");
			env.getCommand("error").execute(env);
			return;
		}
		
		if (!(arg instanceof IReader))	{
			env.pushString("Argument \"" + arg.toString() + "\" is not a reader");
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