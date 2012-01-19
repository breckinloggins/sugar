/**
 * 
 */
package org.sugarlang.command;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseCommand;


/**
 * Pops an argument off the stack and displays an error with that string
 * @author bloggins
 */
public class Error extends BaseCommand {

	@Override
	public String getDescription() {
		return "Pops an argument off the stack and displays an error with that string";
	}

	@Override
	public void execute(Environment env) {
		env.evaluateStack();
		String error = env.pop().toString();
		
		if (null == error)	{
			// TODO: This should be an error cmd
			System.err.println("ERROR (no message)");
			return;
		}
		
		System.err.println("ERROR: " + error);
	}

}
