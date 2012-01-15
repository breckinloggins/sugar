/**
 * 
 */
package com.breckinloggins.cx.command;

import com.breckinloggins.cx.Environment;

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
		String cmdName = env.pop();
		
		if (null == cmdName)	{
			// TODO: This should be an error cmd
			getWriter().println("ERROR: Cannot execute a command because the stack is empty");
			return;
		}
		
		ICommand cmd = env.createCommand(cmdName);
		if (null == cmd)	{
			// TODO: This should be an error cmd
			getWriter().println("ERROR: Command \"" + cmdName + "\" not found");
			return;
		}
		
		cmd.execute(env);
	}

}
