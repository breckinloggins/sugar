/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TypeException;


/**
 * @author bloggins
 * Prints the first argument on the stack
 */
public class Print extends BaseOp {

	public Print() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Prints the first argument on the stack, or nothing if nothing is on the stack";
	}

	@Override
	public void executeInternal(Environment env) throws TypeException {
		env.evaluateStack();
		
		IValue v = env.pop();
		if (null != v)	{
			System.out.print(v.toString());
		}
	}
}
