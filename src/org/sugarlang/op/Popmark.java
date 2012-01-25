/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;

/**
 * Pops everything off the stack up to and including the first stack mark found (or until the stack is empty)
 * @author bloggins
 */
public class Popmark extends BaseOp {

	public Popmark() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Pops everything off the stack up to and including the first stack mark found (or until the stack is empty)";
	}

	@Override
	public void executeInternal(Environment env) {
		env.popToMark();
	}

}
