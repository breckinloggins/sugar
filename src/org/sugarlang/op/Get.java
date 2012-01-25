package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VSymbol;


/**
 * Given a symbol on top of the stack, retrieves the object bound to that symbol and pushes it 
 * on the top of the stack.  The binding must exist
 * @author bloggins
 *
 */
public class Get extends BaseOp {

	public Get() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Given a symbol on top of the stack, retrieves the object bound to that symbol and pushes it on the top of the stack.  The binding must exist";
	}

	@Override
	public void executeInternal(Environment env) throws TypeException {
		
		env.evaluateStack();
		Object sym = env.pop();
		if (null == sym)	{
			env.pushError("Cannot execute get op, the stack is empty");
			return;
		}
		
		if (!(sym instanceof VSymbol))	{
			env.pushError("Cannot execute get op, object on the stack isn't a symbol");
			return;
		}
		
		IValue val = env.getBoundObject((VSymbol)sym);
		if (null == val)	{
			env.pushError("The symbol \"" + ((VSymbol)sym).toString() + "\" is not bound");
			return;
		}
		
		env.push(val);
	}


}
