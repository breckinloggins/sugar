/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VQuote;
import org.sugarlang.value.VSymbol;

/**
 * Sets a binding in the current dictionary.  If the object is quoted, it will be unquoted first
 * @author bloggins
 */
public class Setq extends BaseOp {

	public Setq() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Sets a binding in the current dictionary (top-1: object, top: symbol).  If the object is quoted, it will be unquoted first";
	}

	@Override
	public void executeInternal(Environment env) throws TypeException {
		env.evaluateStack();
		IValue sym = env.pop();
		if (null == sym)	{
			env.pushError("Cannot set binding symbol, stack is empty");
			return;
		}
		
		if (sym instanceof VQuote)	{
			sym = ((VQuote)sym).getInner();
		}
		
		if (!(sym instanceof VSymbol))	{
			env.pushError("Cannot set binding, object on the stack (<" + sym.getType().toString() + ">) isn't a symbol");
			return;
		}
		
		env.evaluateStack();
		IValue v = env.pop();
		if (null == v)	{
			env.pushError("Cannot set binding object, stack is empty");
			return;
		}
		
		if (v instanceof VQuote)	{
			// Unquote the object
			v = ((VQuote)v).getInner();
		}
		
		env.setBinding((VSymbol)sym, v);
	}
}
