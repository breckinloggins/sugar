/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TypeException;


/**
 * Pops an argument off the stack (and does nothing with it)
 * @author bloggins
 *
 */
public class Pop extends BaseOp {

	public Pop() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Pops an argument off the stack";
	}

	@Override
	public void executeInternal(Environment env) throws TypeException {
		IValue popped = env.pop();
		if (null == popped)	{
			env.pushError("The stack is empty");
		}
	}

}
