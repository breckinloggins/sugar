/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseOp;

/**
 * Pops everything off the stack up to and including the first stack mark found (or until the stack is empty)
 * @author bloggins
 */
public class Popmark extends BaseOp {

	@Override
	public String getDescription() {
		return "Pops everything off the stack up to and including the first stack mark found (or until the stack is empty)";
	}

	@Override
	public void execute(Environment env) {
		env.popToMark();
	}

}
