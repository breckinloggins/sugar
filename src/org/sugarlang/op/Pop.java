/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseOp;


/**
 * Pops an argument off the stack (and does nothing with it)
 * @author bloggins
 *
 */
public class Pop extends BaseOp {

	@Override
	public String getDescription() {
		return "Pops an argument off the stack";
	}

	@Override
	public void execute(Environment env) {
		env.evaluateStack();
		Object popped = env.pop();
		if (null == popped)	{
			env.pushString("The stack is empty");
			env.pushOp("error");
		}
	}

}
