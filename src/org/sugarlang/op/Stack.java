/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;


/**
 * Writes the contents of the current stack to stdout
 * @author bloggins
 */
public class Stack extends BaseOp {

	public Stack() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Writes the contents of the current stack to stdout";
	}

	@Override
	public void executeInternal(Environment env) {
		env.printStack(System.out);
	}

}
