/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseOp;


/**
 * @author bloggins
 * Prints the first argument on the stack
 */
public class Print extends BaseOp {

	@Override
	public String getDescription() {
		return "Prints the first argument on the stack, or nothing if nothing is on the stack";
	}

	@Override
	public void execute(Environment env) {
		env.evaluateStack();
		
		Object ent = env.pop();
		if (null != ent)	{
			System.out.print(ent.toString());
		}
	}
}
