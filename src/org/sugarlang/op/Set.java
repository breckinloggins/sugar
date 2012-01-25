/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VSymbol;


/**
 * Sets a binding in the current dictionary
 * @author bloggins
 */
public class Set extends BaseOp {

	public Set() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Sets a binding in the current dictionary (top-1: symbol, top: object)";
	}

	@Override
	public void executeInternal(Environment env) throws TypeException {
		env.evaluateStack();
		IValue v = env.pop();
		if (null == v)	{
			env.pushError("Cannot set binding object, stack is empty");
			return;
		}
		
		env.evaluateStack();
		IValue sym = env.pop();
		if (null == sym)	{
			env.pushError("Cannot set binding symbol, stack is empty");
			return;
		}
		
		if (!(sym instanceof VSymbol))	{
			env.pushError("Cannot set binding, object on the stack (<" + sym.getType().toString() + ">) isn't a symbol");
			return;
		}
		
		env.setBinding((VSymbol)sym, v);
	}

}
