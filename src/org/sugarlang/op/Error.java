/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;


/**
 * Pops an argument off the stack and displays an error with that string
 * @author bloggins
 */
public class Error extends BaseOp {

	public Error() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Pops an argument off the stack and displays an error with that string";
	}

	@Override
	public void executeInternal(Environment env) throws TypeException {
		env.evaluateStack();
		String error = env.pop().toString();
		
		env.pushError(error);
	}

}
