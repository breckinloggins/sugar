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
 * Hides a symbol in the current dictionary, making it appear unbound even if it is bound in a parent dictionary
 * @author bloggins
 *
 */
public class Hide extends BaseOp {

	public Hide() throws TypeException {
		super();
	}
	
	@Override
	public String getDescription() {
		return "Hides a symbol in the current dictionary, making it appear unbound even if it is bound in a parent dictionary (quote the symbol first)";
	}

	/* (non-Javadoc)
	 * @see org.sugarlang.op.BaseOp#executeInternal(org.sugarlang.Environment)
	 */
	@Override
	protected void executeInternal(Environment env) throws Exception {
		if (env.isStackEmpty())	{
			env.pushError("Cannot hide, stack is empty");
			return;
		}
		
		env.evaluateStack();
		IValue v = env.pop();
		if (v instanceof VQuote)	{
			v = ((VQuote)v).getInner();
		}
		
		if (!(v instanceof VSymbol))	{
			env.pushError("Cannot hide, object on the stack isn't a symbol");
			return;
		}
		
		env.hideBinding((VSymbol)v);
	}

}
