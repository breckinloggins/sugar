/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseOp;
import org.sugarlang.type.TSymbol;


/**
 * Sets a binding in the current dictionary
 * @author bloggins
 */
public class Set extends BaseOp {

	@Override
	public String getDescription() {
		return "Sets a binding in the current dictionary (top-1: symbol, top: object)";
	}

	@Override
	public void execute(Environment env) {
		env.evaluateStack();
		Object o = env.pop();
		if (null == o)	{
			env.pushString("Cannot set binding object, stack is empty");
			env.pushOp("error");
			return;
		}
		
		env.evaluateStack();
		Object sym = env.pop();
		if (null == sym)	{
			env.pushString("Cannot set binding symbol, stack is empty");
			env.pushOp("error");
			return;
		}
		
		if (!(sym instanceof TSymbol))	{
			env.pushString("Cannot set binding, object on the stack isn't a symbol");
			env.pushOp("error");
			return;
		}
		
		env.setBinding((TSymbol)sym, o);
	}

}
