/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseOp;


/**
 * Writes the contents of the current stack to stdout
 * @author bloggins
 */
public class Stack extends BaseOp {

	@Override
	public String getDescription() {
		return "Writes the contents of the current stack to stdout";
	}

	@Override
	public void execute(Environment env) {
		env.printStack(System.out);
	}

}
