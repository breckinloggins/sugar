/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VMacro;

/**
 * Base class for all operations that take a macro and two arguments off the stack and perform some test on them
 * @author bloggins
 *
 */
public abstract class BaseTestOp extends BaseOp {

	public BaseTestOp() throws TypeException {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.sugarlang.op.BaseOp#executeInternal(org.sugarlang.Environment)
	 */
	@Override
	protected void executeInternal(Environment env) throws Exception {
		env.evaluateStack();
		if (env.isStackEmpty())	{
			env.pushError("Cannot perform test, not enough arguments");
			return;
		}
		if (!(env.peek() instanceof VMacro))	{
			env.pushError("Cannot perform test, object on the stack isn't a macro");
			return;
		}
		VMacro m = (VMacro)env.pop();
		
		env.evaluateStack();
		if (env.isStackEmpty())	{
			env.pushError("Cannot perform test, stack is empty");
			return;
		}
		IValue v1 = env.pop();
		
		env.evaluateStack();
		if (env.isStackEmpty())	{
			env.pushError("Cannot perform test, not enough arguments");
			return;
		}
		IValue v2 = env.pop();
		
		
		if (testInternal(v1, v2))	{
			env.executeMacro(m);
		}
	}

	/**
	 * Override this function to perform your test between the two values
	 * @param v1 The first value popped off the stack
	 * @param v2 The second value popped off the stack
	 * @return True if the test succeeds, false otherwise
	 */
	protected abstract boolean testInternal(IValue v1, IValue v2);
}
