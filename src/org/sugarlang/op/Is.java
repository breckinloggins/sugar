/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.base.IType;
import org.sugarlang.base.IValue;
import org.sugarlang.type.BuiltinTypes;
import org.sugarlang.type.TypeException;

/**
 * Checks whether the argument at Top-1 is of or inherits from the type specified at Top
 * @author bloggins
 */
public class Is extends BaseOp {

	public Is() throws TypeException {
		super();
		
	}
	
	@Override
	public String getDescription() {
		return "Checks whether the argument at Top-1 is of or inherits from the type specified at Top.  If true, pushes the Type, if false, pushes TNull";
	}

	/* (non-Javadoc)
	 * @see org.sugarlang.op.BaseOp#executeInternal(org.sugarlang.Environment)
	 */
	@Override
	protected void executeInternal(Environment env) throws Exception {
		
		if (env.isStackEmpty())	{
			env.pushError("the stack is empty");
			return;
		}
		
		env.evaluateStack();
		IValue t = env.pop();
		if (!(t instanceof IType))	{
			env.pushError("argument is not a type");
			return;
		}
		
		if (env.isStackEmpty())	{
			env.pushError("the stack is empty");
			return;
		}
		
		env.evaluateStack();
		IValue v = env.pop();
		
		if (v.getType().equals(t))	{
			env.push(v.getType());
		}
		else	{
			env.push(BuiltinTypes.Null);
		}
	}
}
