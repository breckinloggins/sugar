/**
 * 
 */
package org.sugarlang.command;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseCommand;
import org.sugarlang.dictionary.ICommand;


/**
 * Pops an argument off the stack and executes the command by that name
 * @author bloggins
 */
public class Execute extends BaseCommand {

	@Override
	public String getDescription() {
		return "Pops an argument off the stack and executes the command by that name";
	}

	@Override
	public void execute(Environment env) {
		env.evaluateStack();
		String cmdName = env.pop().toString();
		
		if (null == cmdName)	{
			env.pushString("Cannot execute a command because the stack is empty");
			env.getCommand("error").execute(env);
			return;
		}
		
		ICommand cmd = env.getCommand(cmdName);
		if (null == cmd)	{
			env.pushString("ERROR: Command \"" + cmdName + "\" not found");
			env.getCommand("error").execute(env);
			return;
		}
		
		cmd.execute(env);
	}

}