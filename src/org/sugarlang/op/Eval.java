/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VMacro;

/**
 * Causes stack evaluation to occur immediately
 * @author bloggins
 *
 */
public class Eval extends BaseOp {

	public Eval() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Causes stack evaluation to occur immediately (executes any macros if they are present)";
	}
	
	/* (non-Javadoc)
	 * @see org.sugarlang.op.BaseOp#executeInternal(org.sugarlang.Environment)
	 */
	@Override
	protected void executeInternal(Environment env) throws Exception {
		env.evaluateStack();
		if (!env.isStackEmpty() && env.peek() instanceof VMacro)	{
			VMacro m = (VMacro)env.pop();
			env.executeMacro(m);
		}
	}

}
