	/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;

/**
 * Pushes a stack marker on the stack
 * @author bloggins
 */
public class Mark extends BaseOp {

	public Mark() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Pushes a stack marker on the stack";
	}

	@Override
	public void executeInternal(Environment env) {
		env.pushMark();
	}

	
}
