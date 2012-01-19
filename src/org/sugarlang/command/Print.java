/**
 * 
 */
package org.sugarlang.command;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseCommand;


/**
 * @author bloggins
 * Prints the first argument on the stack
 */
public class Print extends BaseCommand {

	@Override
	public String getDescription() {
		return "Prints the first argument on the stack, or nothing if nothing is on the stack";
	}

	@Override
	public void execute(Environment env) {
		env.evaluateStack();
		
		Object ent = env.pop();
		if (null != ent)	{
			System.out.print(ent.toString());
		}
	}
}
