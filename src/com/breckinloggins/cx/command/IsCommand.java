/**
 * 
 */
package com.breckinloggins.cx.command;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseCommand;
import com.breckinloggins.cx.dictionary.ICommand;

/**
 * Peeks at the top of the stack.  If it's a command, pushes true, else pushes false
 * @author bloggins
 */
public class IsCommand extends BaseCommand {

	@Override
	public String getDescription() {
		return "Peeks at the top of the stack.  If it's a command, pushes true, else pushes false";
	}

	@Override
	public void execute(Environment env) {
		
		Object top = env.peek();
		if (null == top)	{
			env.pushString("IsCommand cannot execute, stack is empty");
			env.pushCommand("error");
			return;
		}
		
		if (top instanceof ICommand)	{
			env.push(true);
		}
		else
		{
			env.push(false);
		}
	}

}
