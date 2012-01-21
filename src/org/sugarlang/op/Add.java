/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseOp;


/**
 * Pops two values off the stack, adds them, and pushes the result onto the stack
 * @author bloggins
 *
 */
public class Add extends BaseOp {

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
			env.pushOp("error");
			return;
		}
		
		env.evaluateStack();
		Object arg1 = env.pop();
		if (null == arg1)	{
			env.pushString("Not enough arguments on the stack");
			env.pushOp("error");
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
