/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.base.IValue;
import org.sugarlang.type.TypeException;

/**
 * Pops a macro off the stack and two values to compare.  If the items are not equal, the macro is run
 * @author bloggins
 *
 */
public class Neq extends BaseTestOp {

	public Neq() throws TypeException {
		super();
	}
	
	@Override
	public String getDescription()	{
		return "Pops a macro off the stack and two values to compare.  If the items are not equal, the macro is run";
	}

	@Override
	protected boolean testInternal(IValue v1, IValue v2) {
		return !v1.equals(v2);
	}

}
