/**
 * 
 */
package org.sugarlang.command;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseCommand;


/**
 * Pops an argument off the stack (and does nothing with it)
 * @author bloggins
 *
 */
public class Pop extends BaseCommand {

	@Override
	public String getDescription() {
		return "Pops an argument off the stack";
	}

	@Override
	public void execute(Environment env) {
		env.evaluateStack();
		Object popped = env.pop();
		if (null == popped)	{
			env.pushString("The stack is empty");
			env.getCommand("error").execute(env);
		}
	}

}
