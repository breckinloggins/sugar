/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.type.BuiltinTypes;
import org.sugarlang.type.TypeException;

/**
 * Pushes TNull onto the stack
 * @author bloggins
 */
public class Null extends BaseOp {

	public Null() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Pushes TNull onto the stack";
	}
	
	/* (non-Javadoc)
	 * @see org.sugarlang.op.BaseOp#executeInternal(org.sugarlang.Environment)
	 */
	@Override
	protected void executeInternal(Environment env) throws Exception {
		env.push(BuiltinTypes.Null);
	}

}
