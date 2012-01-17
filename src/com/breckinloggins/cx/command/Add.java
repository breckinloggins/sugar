/**
 * 
 */
package com.breckinloggins.cx.command;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseCommand;
import com.breckinloggins.cx.dictionary.IEntry;

/**
 * Pops two values off the stack, adds them, and pushes the result onto the stack
 * @author bloggins
 *
 */
public class Add extends BaseCommand {

	@Override
	public String getDescription() {
		return "Pops two values off the stack, adds them, and pushes the result onto the stack";
	}

	@Override
	public void execute(Environment env) {
		IEntry arg0 = env.pop();
		if (null == arg0)	{
			env.pushString("Not enough arguments on the stack");
			env.getCommand("error").execute(env);
			return;
		}
		
		IEntry arg1 = env.pop();
		if (null == arg1)	{
			env.pushString("Not enough arguments on the stack");
			env.getCommand("error").execute(env);
			return;
		}
		
		// TODO: This needs to be much more generic and do the appropriate thing for a type
		com.breckinloggins.cx.dictionary.Symbol sym = new com.breckinloggins.cx.dictionary.Symbol();
		sym.setName(arg0.getName() + arg1.getName());
		env.push(sym);
	}

}
