/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;


/**
 * @author bloggins
 *
 */
public class Nop extends BaseOp {

	public Nop() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Does nothing.  Useful when an op is expected but nothing needs to be done.";
	}

	@Override
	public void executeInternal(Environment env) {
	}
}
