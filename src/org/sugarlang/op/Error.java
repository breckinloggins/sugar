/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseOp;


/**
 * Pops an argument off the stack and displays an error with that string
 * @author bloggins
 */
public class Error extends BaseOp {

	@Override
	public String getDescription() {
		return "Pops an argument off the stack and displays an error with that string";
	}

	@Override
	public void execute(Environment env) {
		env.evaluateStack();
		String error = env.pop().toString();
		
		if (null == error)	{
			// TODO: This should be an error op
			System.err.println("ERROR (no message)");
			return;
		}
		
		System.err.println("ERROR: " + error);
	}

}
