/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseOp;


/**
 * @author bloggins
 *
 */
public class Nop extends BaseOp {

	@Override
	public String getDescription() {
		return "Does nothing.  Useful when an op is expected but nothing needs to be done.";
	}

	@Override
	public void execute(Environment env) {
	}
}
