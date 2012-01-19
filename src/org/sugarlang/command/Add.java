/**
 * 
 */
package org.sugarlang.command;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseCommand;


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
		
		env.evaluateStack();
		Object arg0 = env.pop();
		if (null == arg0)	{
			env.pushString("Not enough arguments on the stack");
			env.getCommand("error").execute(env);
			return;
		}
		
		env.evaluateStack();
		Object arg1 = env.pop();
		if (null == arg1)	{
			env.pushString("Not enough arguments on the stack");
			env.getCommand("error").execute(env);
			return;
		}
		
		// TODO: This sucks...
		if (arg0 instanceof Integer && arg1 instanceof Integer)	{
			env.push((Integer)arg0 + (Integer)arg1);
		} else if (arg0 instanceof Double && arg1 instanceof Double)	{
			env.push((Double)arg0 + (Double)arg1);
		} else if (arg0 instanceof Integer && arg1 instanceof Double)	{
			env.push((Double)arg0 + (Integer)arg1);
		} else if (arg0 instanceof Double && arg1 instanceof Integer)	{
			env.push((Double)arg0 + (Integer)arg1);
		} else {
			env.push(arg0.toString() + arg1.toString());
		}
	}

}
