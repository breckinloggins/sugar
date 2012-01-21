/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseOp;

/**
 * Pushes a stack marker on the stack
 * @author bloggins
 */
public class Mark extends BaseOp {

	@Override
	public String getDescription() {
		return "Pushes a stack marker on the stack";
	}

	@Override
	public void execute(Environment env) {
		env.pushMark();
	}

	
}
