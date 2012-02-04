/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VMacro;

/**
 * Pops a macro off the stack and two values to compare.  If the items are equal, the macro is run
 * @author bloggins
 *
 */
public class Eq extends BaseOp {

	public Eq() throws TypeException {
		super();
	}
	
	@Override
	public String getDescription()	{
		return "Pops a macro off the stack and two values to compare.  If the items are equal, the macro is run";
	}

	/* (non-Javadoc)
	 * @see org.sugarlang.op.BaseOp#executeInternal(org.sugarlang.Environment)
	 */
	@Override
	protected void executeInternal(Environment env) throws Exception {
		env.evaluateStack();
		if (env.isStackEmpty())	{
			env.pushError("Cannot eq, not enough arguments");
			return;
		}
		if (!(env.peek() instanceof VMacro))	{
			env.pushError("Cannot eq, object on the stack isn't a macro");
			return;
		}
		VMacro m = (VMacro)env.pop();
		
		env.evaluateStack();
		if (env.isStackEmpty())	{
			env.pushError("Cannot eq, stack is empty");
			return;
		}
		IValue v1 = env.pop();
		
		env.evaluateStack();
		if (env.isStackEmpty())	{
			env.pushError("Cannot eq, not enough arguments");
			return;
		}
		IValue v2 = env.pop();
		
		
		if (v1.equals(v2))	{
			env.executeMacro(m);
		}
		
	}

}
