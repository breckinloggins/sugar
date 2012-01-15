/**
 * 
 */
package com.breckinloggins.cx.command;

import com.breckinloggins.cx.Environment;

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
		String s = env.pop();
		if (null != s)	{
			getWriter().print(s);
		}
	}
}
