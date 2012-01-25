/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;


/**
 * Pops two values off the stack, adds them, and pushes the result onto the stack
 * @author bloggins
 *
 */
public class Add extends BaseOp {

	public Add() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Pops two values off the stack, adds them, and pushes the result onto the stack";
	}

	@Override
	public void executeInternal(Environment env) throws TypeException {
		
		env.evaluateStack();
		Object arg0 = env.pop();
		if (null == arg0)	{
			env.pushError("Not enough arguments on the stack");
			return;
		}
		
		env.evaluateStack();
		Object arg1 = env.pop();
		if (null == arg1)	{
			env.pushError("Not enough arguments on the stack");
			return;
		}
		
		// TODO: Need to implement typeclasses or some such so we'll know what types
		// can be added, subtracted, etc. and how to do so
		env.pushString(arg0.toString() + arg1.toString());
	}

}
