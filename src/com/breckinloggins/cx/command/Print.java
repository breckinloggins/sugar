/**
 * 
 */
package com.breckinloggins.cx.command;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseCommand;
import com.breckinloggins.cx.dictionary.IEntry;

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
		IEntry ent = env.pop();
		if (null != ent)	{
			getWriter().print(ent.getName());
		}
	}
}
