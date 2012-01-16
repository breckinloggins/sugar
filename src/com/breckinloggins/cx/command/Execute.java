/**
 * 
 */
package com.breckinloggins.cx.command;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseCommand;
import com.breckinloggins.cx.dictionary.ICommand;

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
		String cmdName = env.pop().getName();
		
		if (null == cmdName)	{
			// TODO: This should be an error cmd
			getWriter().println("ERROR: Cannot execute a command because the stack is empty");
			return;
		}
		
		ICommand cmd = env.getCommand(cmdName);
		if (null == cmd)	{
			// TODO: This should be an error cmd
			getWriter().println("ERROR: Command \"" + cmdName + "\" not found");
			return;
		}
		
		cmd.execute(env);
	}

}
